# ATLAS — Local AI Personal Assistant

A **ATLAS** é um projeto de assistente pessoal desenvolvido em **Java**, criado para estudar e aplicar conceitos de desenvolvimento de software, integração com Inteligência Artificial local, processamento de voz e automação do sistema.

O projeto começou como um experimento pessoal inspirado em assistentes virtuais como o Jarvis e evoluiu gradualmente para uma aplicação com diferentes componentes, incluindo **interface gráfica, entrada por voz, processamento de linguagem natural e execução de comandos no sistema operacional**.

> **Status:** Em desenvolvimento contínuo.

---

## Objetivos

O principal objetivo da ATLAS é servir como um laboratório prático para estudar e integrar diferentes áreas do desenvolvimento de software.

Entre os principais objetivos estão:

* Desenvolver uma aplicação de maior escala utilizando Java;
* Praticar Programação Orientada a Objetos;
* Integrar aplicações Java com ferramentas e serviços externos;
* Executar modelos de IA localmente;
* Implementar interação por voz;
* Automatizar tarefas no sistema operacional;
* Desenvolver uma interface gráfica;
* Experimentar conceitos de memória e contexto para assistentes de IA;
* Aprender sobre arquitetura e organização de projetos.

---

## Arquitetura

A ATLAS é composta por diferentes componentes que trabalham em conjunto:

```text
┌──────────────────────────┐
│       Interface UI       │
│         JavaFX           │
└────────────┬─────────────┘
             │
             ▼
┌──────────────────────────┐
│      Core da ATLAS       │
│          Java            │
└──────┬─────────┬─────────┘
       │         │
       ▼         ▼
┌───────────┐ ┌──────────────┐
│   Voz     │ │     IA       │
│ Whisper   │ │   Ollama     │
└───────────┘ └──────────────┘
                    │
                    ▼
              ┌──────────┐
              │  Qwen    │
              └──────────┘
```

A arquitetura ainda está em evolução e é constantemente refatorada conforme novos conceitos são aprendidos.

---

# Tecnologias

## Backend / Aplicação

* **Java**
* **Maven**
* **JavaFX**

## Inteligência Artificial

* **Ollama**
* **Qwen 2.5**

Modelos utilizados durante o desenvolvimento:

* `qwen2.5:3b`
* `qwen2.5:7b`

A utilização de modelos locais permite executar a parte de processamento de IA diretamente no computador, sem depender exclusivamente de APIs externas.

## Processamento de Voz

* **Whisper**
* **Python**
* **FFmpeg**

O pipeline de voz utiliza o Whisper para transformar áudio em texto, permitindo que comandos sejam enviados para o núcleo da aplicação.

---

# Funcionalidades

Atualmente, o projeto explora e implementa funcionalidades como:

* Interação por texto;
* Reconhecimento de voz;
* Integração com modelos de IA locais;
* Interface gráfica utilizando JavaFX;
* Execução de comandos no sistema;
* Pipeline de entrada e processamento de áudio;
* Memória permanente (básica);
* Experimentação com memória e contexto;
* Arquitetura preparada para expansão.

Algumas dessas funcionalidades ainda estão em desenvolvimento e podem sofrer alterações entre versões.

---

# Sistema Operacional

O desenvolvimento é realizado principalmente em **Linux**.

Distribuições utilizadas durante o desenvolvimento:

* Ubuntu
* Arch Linux

O ambiente Linux também faz parte do processo de aprendizado do projeto, principalmente nas áreas de automação, processos, terminal e integração com o sistema operacional.

---

# Requisitos

Para executar o projeto, são necessários:

* Java
* Maven
* Python 3
* pip
* FFmpeg
* Ollama
* Git

Dependendo da versão da ATLAS, outros componentes podem ser necessários.

---

# Instalação

Clone o repositório:

```bash
git clone https://github.com/ArthurV-20/ATLAS-VERSION-1.git
```

Entre no diretório:

```bash
cd ATLAS-VERSION-1
```

As instruções específicas de execução podem variar de acordo com a versão do projeto.

> **Nota:** A documentação de instalação ainda está sendo aprimorada conforme a arquitetura da ATLAS evolui.

---

# O que estou aprendendo com a ATLAS

A ATLAS não foi criada apenas para produzir um assistente virtual. O projeto funciona principalmente como um ambiente de experimentação e aprendizado.

Durante seu desenvolvimento, venho estudando e aplicando conceitos como:

* Java e Programação Orientada a Objetos;
* Estruturação de projetos com Maven;
* Desenvolvimento de aplicações JavaFX;
* Integração entre diferentes tecnologias;
* APIs e comunicação entre processos;
* Processamento de áudio;
* Inteligência Artificial local;
* Linux e automação;
* Arquitetura de software;
* Separação de responsabilidades;
* Debugging e resolução de problemas;
* Refatoração e evolução incremental.

---

# Evolução do Projeto

A ATLAS foi construída de forma incremental.

Em vez de tentar desenvolver um sistema complexo desde o início, o projeto começou com funcionalidades simples e foi recebendo novos componentes conforme meus conhecimentos aumentaram.

O histórico de versões representa parte dessa evolução:

```text
ATLAS
│
├── Versões iniciais
│   └── Experimentos com Java e IA
│
├── Integração com Ollama
│   └── Execução de modelos locais
│
├── Sistema de voz
│   └── Whisper + processamento de áudio
│
├── Interface gráfica
│   └── JavaFX
│
└── Versões atuais
    └── Refatoração + expansão da arquitetura
```

Uma das propostas do projeto é justamente manter esse histórico para demonstrar minha evolução técnica ao longo do tempo.

---

# Próximos Passos

Algumas das funcionalidades planejadas para versões futuras incluem:

* Melhorar a arquitetura interna;
* Expandir o sistema de memória;
* Melhorar o gerenciamento de contexto;
* Expandir a automação do sistema;
* Melhorar a interface JavaFX;
* Reduzir a latência da interação por voz;
* Implementar testes automatizados;
* Melhorar documentação e organização do código.

---

## Sobre o Projeto

A ATLAS é um projeto pessoal desenvolvido durante meus estudos de programação.

Mais do que criar um "assistente virtual", meu objetivo é utilizar o projeto como uma forma de **aprender desenvolvimento de software construindo algo real**, enfrentando problemas de integração, arquitetura, desempenho e manutenção.

> **Build. Break. Learn. Improve.**
>
> Uma versão de cada vez.
