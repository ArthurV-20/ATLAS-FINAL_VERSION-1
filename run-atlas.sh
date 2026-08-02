#!/bin/bash

echo "================================"
echo "        Iniciando ATLAS"
echo "================================"

echo ""

echo "Verificando Ollama..."

if ! command -v ollama &> /dev/null
then
    echo "Ollama não encontrado."
    exit 1
fi


echo "Iniciando Ollama..."

ollama serve > /dev/null 2>&1 &


echo ""

echo "Iniciando aplicação..."

mvn javafx:run
