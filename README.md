<div align="center">
    <h1>🐾 Sistema de Gerenciamento de Clínica Veterinária 🐾</h1>
</div>

<div align="center">
    <img src="https://github.com/user-attachments/assets/2c96873f-1ed9-4fd9-97f3-6878c37b0e78" alt="image" style="height: 400px;">
</div>

## 📋 Sobre o Projeto
Este é um sistema desenvolvido para auxiliar no gerenciamento de clínicas veterinárias, oferecendo funcionalidades para cadastro e gerenciamento de clientes, veterinários, tratamentos e mais. O projeto foi implementado em Java, utilizando a biblioteca Swing para a interface gráfica, e o banco de dados H2 para armazenamento.

## 📋 Funcionalidades
- Gerenciamento de Clientes: Cadastro, edição, exclusão, busca e listagem de clientes.
- Gerenciamento de Animais: Cadastro, edição, exclusão e listagem de veterinários.
  - Cada animal é associado a um cliente.
  - Cada animal possui uma classe específica.
  - São exibidos somente os animais relacionados ao cliente selecionado.
- Gerenciamento de Veterinários: Cadastro, edição, exclusão, busca e listagem de veterinários.
  - Cada veterinário é associado a uma consulta.
  - Cada veterinário possui uma especialidade relacionada a uma classe de animal.
  - Ao selecionar um animal, as primeiras opções de veterinários são sempre aquelas cuja especialidade está atrelada à classe do animal.
- Gerenciamento de Tratamentos: Criação, edição, exclusão e listagem de consultas..
  - Todas as consultas relacionadas a um animal.
  - São exibidos somente os tratamentos relacionados ao animal selecionado.
- Gerenciamento de Consultas: Cadastro, edição, exclusão e listagem de consultas.
  - É possível ver todas as consultas já cadastradas, podendo ordená-las por ordem de inserção no banco, da mais nova para a mais antiga ou procurar de acordo com uma data.
  - O sistema garante que não haja conflito de consultas, averiguando se existe outra consulta com o mesmo veterinário, horário e dia durante o cadastro.
  - Todas as consultas são associadas a um tratamento.
  - Também possível ver todas as consultas associadas ao tratamento selecionado.
- Gerenciamento de Pagamentos: Criação, edição, exclusão e listagem de pagamentos.
  - Cada pagamento está relacionado a uma consulta.
  - Durante o cadastro da consulta, são infomados os dados do pagamento.
  - São exibidos somente os pagamentos relacionados a consulta selecionada.
- Gerenciamento de Exames: Criação, edição, exclusão e listagem de pagamentos.
  - Cada exame está relacionado a uma consulta.
  - Os exames são adicionados após a criação da consulta, mediante solicitação do veterinário.
  -  São exibidos somente os exames relacionados a consulta selecionada.
- Relatórios: Geração de relatórios básicos sobre os clientes, animais, veterinários, tratamentos, consultas, exames e pagamentos.

## 💻 Tecnologias Utilizadas
- **Linguagem:** Java
- **Frameworks/Libs:** 
  - Swing (Interface Gráfica)
  - `lombok`
- **Banco de Dados:** H2
- **Arquitetura:** MVC

## 📖 Como Usar
### Requisitos
- Java 17 ou superior instalado no sistema.
- IDE de sua escolha ou terminal para compilar e executar os arquivos.
- Maven configurado.

### Passos
1. Inicie a aplicação executando a classe Principal.

2. Navegue pelas abas ou menus para acessar as funcionalidades:
    - Selecionar Cliente e Animal: Gerenciar informações de clientes e animais.
    - Agendar Consulta: Gerenciar informações de veterinários e cadastrar consultas.
    - Acompanhar Tratamentos: Gerenciar tratamentos e vínculos com consultas.
    - Acompanhar Consultas: Listagem e ordenação de todas as consultas disponíveis no banco, não somente aquelas relacionadas a um tratamento de um animal.

<hr>
