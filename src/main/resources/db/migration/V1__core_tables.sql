CREATE TABLE IF NOT EXISTS task (
                                     id UUID PRIMARY KEY,
                                     title VARCHAR(120) NOT NULL,
    description VARCHAR(2000),
    status VARCHAR(30) NOT NULL,
    due_date TIMESTAMP WITH TIME ZONE NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
                             );

CREATE INDEX IF NOT EXISTS idx_task_status ON task(status);
CREATE INDEX IF NOT EXISTS idx_task_updated_at ON task(updated_at);
