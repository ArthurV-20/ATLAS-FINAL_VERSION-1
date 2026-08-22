#!/bin/bash
export GDK_SCALE=1
export GDK_DPI_SCALE=1
export JAVA_TOOL_OPTIONS="-Dglass.gtk.uiScale=1.5"
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
