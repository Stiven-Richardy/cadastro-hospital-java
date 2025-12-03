# 🏥 Projeto: Cadastro de Pacientes (Java Swing)

Este projeto foi desenvolvido como **Trabalho Prático Substitutivo (PII)** da disciplina de **Linguagem de Programação II**, com o objetivo de criar um sistema de gerenciamento hospitalar simples. A aplicação permite o cadastro, consulta e visualização de dados de pacientes, integrando uma interface gráfica Java com um banco de dados **SQL Server**.

## 🎯 Objetivos

- **Interface Gráfica**: Desenvolver uma tela de cadastro contendo campos obrigatórios: Nome, Idade, Peso e Altura.
- **Persistência de Dados**: Implementar a inclusão de registros no banco de dados, validando a consistência dos tipos de dados (int, float).
- **Pesquisa Dinâmica:** Implementar a busca de pacientes por nome utilizando a cláusula SQL `LIKE`.
- **Visualização:** Apresentar os dados cadastrados ou filtrados através de componentes visuais (`JOptionPane`).
- **Funcionalidades Extras:** Implementar botões para limpar formulário e encerrar a aplicação.

## 🛠️ Ferramentas Utilizadas

- Java
- SQL Server
- JDBC
- VS Code
- Git e GitHub

## 🗄️ Estrutura do Banco de Dados

O projeto utiliza o banco de dados **SQL Server** (database `hospital`) e cria automaticamente a tabela `pessoas` caso ela não exista.

#### Tabela: `pessoas`
| Column Name | Data Type | Allow Nulls | Descrição |
| :--- | :--- | :--- | :--- |
| **id** | `VARCHAR(40)` | Não | Chave Primária (PK) - Gerado via UUID |
| **nome** | `VARCHAR(50)` | Sim | Nome do paciente |
| **idade** | `INT` | Sim | Idade do paciente |
| **peso** | `FLOAT` | Sim | Peso do paciente (kg) |
| **altura** | `FLOAT` | Sim | Altura do paciente (m) |

## 🗂️ Estrutura do Projeto

```
📁 projeto-hospital-java/
├── 📁 lib/
│   └── 📄 mssql-jdbc-13.2.1.jre8.jar
├── 📄 Form.java
├── 📄 Pessoa.java
├── 📄 .gitignore
└── 📄 README.md
```

## 🚀 Como Executar

1. Configuração do Ambiente:
- Certifique-se de ter o **SQL Server** rodando localmente na porta `1433`.
- O código cria a tabela automaticamente, mas certifique-se de que o banco de dados definido na string de conexão (`hospital`) exista ou ajuste a URL no código.

2. Clone o repositório:
```bash
git clone https://github.com/Stiven-Richardy/cadastro-hospital-java
```

3. Acesse a pasta do projeto:
```bash
cd cadastro-hospital-java
```

4. Compile os arquivos:
```bash
javac -encoding UTF-8 -cp ".;lib/mssql-jdbc-13.2.1.jre8.jar" Form.java
```

5. Execute os programas (Um de cada vez):
```bash
java -cp ".;lib/mssql-jdbc-13.2.1.jre8.jar" Form
```

## 👨‍🏫 Autores

- **Stiven Richardy Silva Rodrigues**  
  Estudante de Análise e Desenvolvimento de Sistemas | IFSP — Campus Cubatão  
  [@Stiven-Richardy](https://github.com/Stiven-Richardy)

- **Guilherme Mendes de Sousa**  
  Estudante de Análise e Desenvolvimento de Sistemas | IFSP — Campus Cubatão  
  [@Guilh3rme-M3ndes](https://github.com/Guilh3rme-M3ndes)

## 📚 Referências

- Documentação oficial do Java: https://docs.oracle.com/en/java/
- Baixar o JDBC Driver para SQL Server: https://learn.microsoft.com/pt-br/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver17
