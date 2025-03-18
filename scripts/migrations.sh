#!/bin/bash

if [ -f .env ]; then
    export $(grep -v '^#' .env | xargs)
else
    echo ".env file not found!"
    exit 1
fi

if ! command -v psql &> /dev/null; then
    echo "psql não encontrado. Instalando PostgreSQL..."
    
    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
        sudo apt update && sudo apt install -y postgresql-client
    elif [[ "$OSTYPE" == "darwin"* ]]; then
        brew install postgresql
    else
        echo "Sistema operacional não suportado para instalação automática."
        exit 1
    fi
fi

PGPASSWORD="$POSTGRES_PASSWORD" psql -h "$PG_HOST" -U "$POSTGRES_USER" -d "$PG_DATABASE" -f src/main/resources/db/migrations/V1__seed_watchables.sql
