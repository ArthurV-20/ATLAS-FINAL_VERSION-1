#!/bin/bash
check_command() {
    if command -v "$1" &> /dev/null
    then
        echo "$1 encontrado."
    else
        echo "$1 não encontrado."
    fi
}
install_package() {

    PACKAGE=$1

    if [ "$PACKAGE_MANAGER" = "apt" ]
    then
        sudo apt update
        sudo apt install -y "$PACKAGE"

    elif [ "$PACKAGE_MANAGER" = "pacman" ]
    then
        sudo pacman -Sy --noconfirm "$PACKAGE"

    else
        echo "Gerenciador desconhecido."
        exit 1
    fi

}

echo "================================"
echo "        ATLAS Installer"
echo "================================"

echo ""

echo "Verificando sistema..."

if [ -f /etc/os-release ]
then
    source /etc/os-release

    DISTRO=$ID

    echo "Distribuição detectada:"
    echo "$NAME"

else
    echo "Não foi possível detectar o sistema."
    exit 1
fi
echo ""

case "$DISTRO" in

ubuntu|debian)
    PACKAGE_MANAGER="apt"
    echo "Gerenciador de pacotes: apt"
    ;;

arch)
    PACKAGE_MANAGER="pacman"
    echo "Gerenciador de pacotes: pacman"
    ;;

*)
    PACKAGE_MANAGER="unknown"
    echo "Gerenciador de pacotes desconhecido"
    ;;

esac

echo ""

echo "Verificando dependências básicas..."

check_command java
check_command mvn
check_command ffmpeg
check_command python3
check_command pip3
check_command git

echo ""

echo "Diretório da ATLAS:"
pwd

echo ""

echo "Verificando Java..."

if command -v java &> /dev/null
then
    echo "Java encontrado."

else

    echo "Java não encontrado."

    if [ "$PACKAGE_MANAGER" = "apt" ]
    then
        install_package openjdk-21-jdk

    elif [ "$PACKAGE_MANAGER" = "pacman" ]
    then
        install_package jdk21-openjdk
    fi

fi
echo ""

echo "Verificando Maven..."

if command -v mvn &> /dev/null
then
    echo "Maven encontrado."
    mvn -version | head -n 1

else

    echo "Maven não encontrado."
    echo "Instalando Maven..."

    if [ "$PACKAGE_MANAGER" = "apt" ]
    then
        install_package maven

    elif [ "$PACKAGE_MANAGER" = "pacman" ]
    then
        install_package maven
    fi

fi

echo ""
echo ""

echo "Verificando Ollama..."

if command -v ollama &> /dev/null
then
    echo "Ollama encontrado:"
    ollama --version

else

    echo "Ollama não encontrado."
    echo "Instalando Ollama..."

    curl -fsSL https://ollama.com/install.sh | sh

fi

echo ""

echo "Verificando modelos de IA..."

MODELS=$(ollama list)
if ! ollama list | grep -q "qwen2.5:3b"
then
    echo "Baixando qwen2.5:3b..."
    ollama pull qwen2.5:3b
fi


if ! ollama list | grep -q "qwen2.5:7b"
then
    echo "Baixando qwen2.5:7b..."
    ollama pull qwen2.5:7b
fi

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

    if [ "$PACKAGE_MANAGER" = "apt" ]
    then
        install_package python3 python3-pip python3-venv

    elif [ "$PACKAGE_MANAGER" = "pacman" ]
    then
        install_package python python-pip
    fi

fi


echo ""

echo "Verificando ambiente virtual Python..."

if [ -d "python/venv" ]
then
    echo "Ambiente virtual encontrado."

else
    echo "Criando ambiente virtual..."

    python3 -m venv python/venv

    echo "Ambiente virtual criado."
fi


echo ""

echo "Instalando dependências Python..."

python/venv/bin/pip install -r python/requirements.txt

echo "Dependências Python concluídas."

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
    echo "FFmpeg encontrado:"
    ffmpeg -version | head -n 1

else

    echo "FFmpeg não encontrado."

    if [ "$PACKAGE_MANAGER" = "apt" ]
    then
        install_package ffmpeg

    elif [ "$PACKAGE_MANAGER" = "pacman" ]
    then
        install_package ffmpeg

    fi

fi

echo ""

echo "Verificando ambiente Python..."

if [ -d "python/venv" ]
then
    echo "Ambiente virtual encontrado."
else
    echo "Criando ambiente virtual..."

    python3 -m venv python/venv

    echo "Ambiente virtual criado."
fi
echo ""

echo "Verificando dependências Python..."

python/venv/bin/pip install -r python/requirements.txt

echo "Dependências Python verificadas."
echo ""

echo "Compilando ATLAS..."

mvn clean package

if [ $? -eq 0 ]
then
    echo "ATLAS compilada com sucesso."
else
    echo "Erro ao compilar ATLAS."
    exit 1
fi
echo ""

echo "Compilando ATLAS..."

mvn clean package

if [ $? -eq 0 ]
then
    echo ""
    echo "================================"
    echo " ATLAS compilada com sucesso!"
    echo "================================"
else
    echo ""
    echo "Erro ao compilar ATLAS."
    exit 1
fi
echo "Verificação inicial concluída."
