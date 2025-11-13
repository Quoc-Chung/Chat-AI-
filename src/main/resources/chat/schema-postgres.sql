CREATE TABLE IF NOT EXISTS spring_ai_chat_memory (
                                                     id SERIAL PRIMARY KEY,
                                                     conversation_id VARCHAR(255) NOT NULL,
    message_index INTEGER NOT NULL,
    content TEXT,
    role VARCHAR(20),
    name VARCHAR(255),
    function_call JSONB,
    tool_calls JSONB,
    tool_call_id VARCHAR(255),
    metadata JSONB,
    type VARCHAR(255),
    timestamp TIMESTAMP
    );
