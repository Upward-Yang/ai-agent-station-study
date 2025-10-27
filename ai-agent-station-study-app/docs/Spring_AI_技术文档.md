# Spring AI 框架技术文档

## 1. 核心概念

Spring AI 是一个基于 Spring 生态系统的应用框架，旨在简化人工智能（AI）功能在企业级 Java 应用中的集成。其核心目标是解决 AI 集成的根本挑战：将企业内部的数据、API 和工具与外部的 AI 模型（如大语言模型 LLMs）无缝连接起来。

### 1.1 主要特性

- **统一抽象 (Unified Abstractions)**: Spring AI 提供了一套标准的编程接口，屏蔽了不同 AI 服务提供商（如 OpenAI, Azure, Google, Hugging Face 等）的底层差异。开发者可以使用相同的代码调用不同的模型，实现“一套代码，多种 AI 服务”的可移植性。
- **多模态支持 (Multimodal Support)**: 不仅支持文本处理，还原生支持图像、音频等多模态输入和输出。
- **向量数据库集成 (Vector Store Integration)**: 内置对多种主流向量数据库的支持（如 PGVector, Chroma, Redis, Qdrant 等），为实现检索增强生成（RAG）提供了便利。
- **Model Context Protocol (MCP)**: 支持 MCP 协议，允许应用程序以标准化的方式将数据源和工具暴露给 AI 模型，从而构建更复杂的 AI Agent 和工作流。
- **与 Spring Boot 无缝集成**: 作为 Spring 生态的一部分，它充分利用了 Spring Boot 的自动配置、依赖注入等特性，让 AI 功能的集成变得像添加一个普通的 Spring 组件一样简单。

### 1.2 核心组件

- **ChatModel**: 用于与聊天模型进行交互的核心接口，支持同步 (`call`) 和流式 (`stream`) 调用。
- **EmbeddingModel**: 用于将文本或其他数据转换为向量表示的模型，是 RAG 实现的关键。
- **VectorStore**: 用于存储和检索向量数据的接口，实现了与各种向量数据库的解耦。
- **ChatClient**: 一个高级 API，提供了一种声明式的方式来构建与 AI 模型的对话，支持提示词模板、参数化系统消息、聊天记忆和顾问（Advisors）等高级功能。
- **Advisor**: 一种拦截器模式，用于在请求发送到模型前或响应返回后修改上下文。常用于实现聊天记忆、RAG 和日志记录等功能。

## 2. 使用示例

### 2.1 基本聊天模型调用

```java
@Autowired
private ChatModel chatModel;

public String simpleChat(String userMessage) {
    Prompt prompt = new Prompt(new UserMessage(userMessage));
    return chatModel.call(prompt).getResult().getOutput().getContent();
}
```

### 2.2 配置向量存储 (PGVector)

根据项目配置，Spring AI 可以与 PostgreSQL 的 `pgvector` 扩展集成，创建一个名为 `vector_store_openai` 的表来存储嵌入向量。

```java
@Configuration
public class AiAgentConfig {

    @Bean("vectorStore")
    public PgVectorStore pgVectorStore(@Value("${spring.ai.openai.base-url}") String baseUrl,
                                       @Value("${spring.ai.openai.api-key}") String apiKey,
                                       @Value("${spring.ai.openai.embedding-model}") String embeddingModelStr,
                                       @Value("${spring.ai.openai.embedding.options.num-batch}") Integer dimensions,
                                       @Qualifier("pgVectorJdbcTemplate") JdbcTemplate jdbcTemplate) {

        OpenAiApi openAiApi = OpenAiApi.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .build();

        OpenAiEmbeddingOptions openAiEmbeddingOptions =
                OpenAiEmbeddingOptions.builder()
                        .model(embeddingModelStr).dimensions(dimensions).build();

        OpenAiEmbeddingModel embeddingModel = new OpenAiEmbeddingModel(openAiApi, MetadataMode.EMBED, openAiEmbeddingOptions);
        
        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
                .vectorTableName("vector_store_openai")
                .build();
    }
}
```

### 2.3 实现检索增强生成 (RAG)

通过自定义 `Advisor` 来实现 RAG，将检索到的上下文信息注入到用户请求中。

```java
public class RagAnswerAdvisor implements BaseAdvisor {
    private final VectorStore vectorStore;
    private final SearchRequest searchRequest;

    public RagAnswerAdvisor(VectorStore vectorStore, SearchRequest searchRequest) {
        this.vectorStore = vectorStore;
        this.searchRequest = searchRequest;
    }

    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        // 1. 从原始请求中获取用户问题
        String userText = chatClientRequest.prompt().getUserMessage().getText();
        
        // 2. 在向量数据库中进行相似性搜索
        List<Document> documents = vectorStore.similaritySearch(
            SearchRequest.from(searchRequest).query(userText).build()
        );
        
        // 3. 将检索到的文档拼接为上下文字符串
        String documentContext = documents.stream().map(Document::getText)
            .collect(Collectors.joining(System.lineSeparator()));
        
        // 4. 构建新的用户消息，包含原始问题和上下文
        String advisedUserText = userText + "\nContext information is below...\n" + documentContext;
        
        return ChatClientRequest.builder()
                .prompt(Prompt.builder().messages(new UserMessage(advisedUserText)).build())
                .context(chatClientRequest.context())
                .build();
    }
    
    // ... after 方法和其他实现
}
```

### 2.4 配置 ChatClient 与 Advisors

使用 `ChatClient` 可以方便地组合多个 `Advisor`，例如同时启用聊天记忆和 RAG。

```java
@Bean
public ChatClient chatClient(ChatModel chatModel, PgVectorStore vectorStore) {
    return ChatClient.builder(chatModel)
            .defaultSystem("你是一个专业的AI助手，可以根据知识库回答问题。")
            .defaultAdvisors(
                // 使用消息窗口记忆，保留最近100条消息
                PromptChatMemoryAdvisor.builder(
                    MessageWindowChatMemory.builder().maxMessages(100).build()
                ).build(),
                // 使用RAG顾问
                new RagAnswerAdvisor(vectorStore, SearchRequest.builder().topK(5).build()),
                // 启用简单日志记录
                new SimpleLoggerAdvisor()
            )
            .build();
}
```

### 2.5 集成 Model Context Protocol (MCP)

Spring AI 支持 MCP 客户端，可以连接到 MCP 服务器以访问文件系统、数据库等外部工具。

```java
// 在测试类中创建 MCP 客户端的示例
public McpSyncClient sseMcpClient01() {
    HttpClientSseClientTransport sseClientTransport = 
        HttpClientSseClientTransport.builder("http://192.168.1.108:8102").build();
    
    McpSyncClient mcpSyncClient = McpClient.sync(sseClientTransport)
        .requestTimeout(Duration.ofMinutes(180)).build();
    
    var init = mcpSyncClient.initialize();
    System.out.println("SSE MCP Initialized: " + init);
    
    return mcpSyncClient;
}

// 在 ChatModel 中注册 MCP 工具回调
chatModel = OpenAiChatModel.builder()
    .openAiApi(openAiApi)
    .defaultOptions(OpenAiChatOptions.builder()
        .toolCallbacks(new SyncMcpToolCallbackProvider(sseMcpClient01()).getToolCallbacks())
        .build())
    .build();
```

## 3. 最佳实践

### 3.1 性能优化

- **向量维度**: 确保 `embedding-model` 的维度与向量数据库表中 `embedding` 字段的维度一致（如项目中的 1536）。不匹配会导致错误。
- **连接池配置**: 为数据库和向量数据库配置合适的连接池（如 HikariCP），避免因连接不足导致性能瓶颈。
- **异步处理**: 对于耗时较长的 AI 调用，考虑使用 `stream` 或异步方法，避免阻塞主线程。

### 3.2 配置建议

- **环境隔离**: 使用 `application-dev.yml`, `application-prod.yml` 等配置文件来区分不同环境的设置，如 API 密钥、数据库地址等。
- **敏感信息管理**: 将 API 密钥等敏感信息存储在环境变量或配置中心，而不是硬编码在代码或配置文件中。
- **超时设置**: 为 HTTP 客户端配置合理的连接和读取超时时间，防止请求无限期挂起。

### 3.3 常见问题

- **MCP 连接失败**: 确保 MCP 服务器已正确启动，并且客户端配置的 URL 和端口无误。
- **RAG 效果不佳**: 检查文档切分策略是否合理，确保检索到的上下文确实与问题相关。可以调整 `topK` 值或优化 `filterExpression`。
- **模型调用报错**: 仔细检查 `base-url` 和 `api-key` 是否正确，确认所使用的模型名称在服务提供商处是可用的。
