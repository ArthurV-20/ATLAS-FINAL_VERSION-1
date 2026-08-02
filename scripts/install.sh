#!/bin/bash

echo "================================"
echo "        ATLAS Installer"
echo "================================"

echo ""

echo "Verificando sistema..."

if [ -f /etc/os-release ]; then
    source /etc/os-release
    echo "Sistema detectado: $NAME"
else
    echo "Não foi possível detectar o sistema."
fi

echo ""

echo "Diretório da ATLAS:"
pwd

echo ""

echo "Verificando Java..."

if command -v java &> /dev/null
then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1)
    echo "Java encontrado:"
    echo "$JAVA_VERSION"
else
    echo "Java não encontrado."
fi


echo ""

echo "Verificando Maven..."

if command -v mvn &> /dev/null
then
    MVN_VERSION=$(mvn -version | head -n 1)
    echo "Maven encontrado:"
    echo "$MVN_VERSION"
else
    echo "Maven não encontrado."
fi


echo ""
echo ""

echo "Verificando Ollama..."

if command -v ollama &> /dev/null
then
    OLLAMA_VERSION=$(ollama --version)
    echo "Ollama encontrado:"
    echo "$OLLAMA_VERSION"
else
    echo "Ollama não encontrado."
fi
echo ""

echo "Verificando modelos de IA..."

MODELS=$(ollama list)

echo "$MODELS"

echo ""

echo "Verificação de modelos concluída."
echo ""

echo "Verificando modelos de IA..."

check_model() {
    if ollama list | grep -q "$1"; then
        echo "$1 encontrado."
    else
        echo "$1 não encontrado."
    fi
}

check_model "qwen2.5:3b"
check_model "qwen2.5:7b"

echo ""

echo "Verificação de modelos concluída."
echo ""

echo "Verificando Python..."

if command -v python3 &> /dev/null
then
    PYTHON_VERSION=$(python3 --version)
    echo "Python encontrado:"
    echo "$PYTHON_VERSION"
else
    echo "Python não encontrado."
fi
echo ""

echo "Verificando pip..."

if command -v pip3 &> /dev/null
then
    PIP_VERSION=$(pip3 --version)
    echo "pip encontrado:"
    echo "$PIP_VERSION"
else
    echo "pip não encontrado."
fi
echo ""

echo "Verificando FFmpeg..."

if command -v ffmpeg &> /dev/null
then
    FFMPEG_VERSION=$(ffmpeg -version | head -n 1)
    echo "FFmpeg encontrado:"
    echo "$FFMPEG_VERSION"
else
    echo "FFmpeg não encontrado."
fi
echo "Verificação inicial concluída."
