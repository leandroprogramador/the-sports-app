# 🏆 The Sports App

Aplicativo Android moderno para consulta de **países, ligas e detalhes esportivos**, utilizando a API **TheSportsDB**.  
O projeto foi desenvolvido com foco em **arquitetura limpa**, **testabilidade**, **offline-first** e **UI moderna com Jetpack Compose**.

---

## 📱 Funcionalidades

- Listagem de países por esporte
- Pesquisa dinâmica por países
- Listagem de ligas por país e esporte
- Pesquisa de ligas por nome
- Tela de detalhes da liga
- Suporte a múltiplos idiomas (PT / EN)
- Offline-first com cache local
- Tratamento de estados (Loading, Success, Error)
- Testes unitários e instrumentados

---

## 🧱 Arquitetura

O projeto segue **Clean Architecture + MVVM**, com separação clara de responsabilidades, seguindo princípios SOLID e Clean Code.


### Padrões utilizados
- MVVM
- Repository Pattern
- Domain
- Use Cases
- StateFlow / Flow
- Unidirectional Data Flow (UDF)

---

## 🛠️ Stack Tecnológica

### Core
- Kotlin
- Coroutines
- Flow
- StateFlow

### UI
- Jetpack Compose
- MVVM
- Material 3
- Coil (imagens)
- Navigation 3 (Compose)

### Persistência
- Room (cache local)
- Offline-first strategy

### Injeção de Dependência
- Koin

### Networking
- Retrofit
- OkHttp
- Gson

### Testes
- JUnit4
- MockK
- Turbine
- Coroutines Test
- Robolectric
- Compose UI Test

---

## 🧪 Testes

O projeto possui cobertura de testes em diferentes camadas:

### Testes Unitários e Integração
- ViewModels
- UseCases
- Repositórios
- Datasources

### Testes Instrumentados
- Telas em Jetpack Compose
- Fluxo de pesquisa
- Estados vazios e erros

Os testes validam:
- Estados da UI
- Emissões de Flow
- Comportamento de busca
- Reatividade da interface

---

## 🌍 Internacionalização

- Caso o idioma do dispositivo seja **Português**, será exibido nessa língua. Caso contrário, o padrão é **Inglês**.

---

## 🎨 UI / UX

- Interface construída 100% com **Jetpack Compose**
- Componentes reutilizáveis
- Estados visuais claros (loading, empty, error)
- Ícones de fallback quando imagens não estão disponíveis

---

## 📦 Gerenciamento de Dependências

- Version Catalog (`libs.versions.toml`)
- Dependências centralizadas
- Fácil manutenção e upgrade

---

## 🚀 Como rodar o projeto

1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/the-sports-app.git
```

2. Abra no Android Studio (Hedgehog ou superior)

3. Sincronize o Gradle

4. Execute no emulador ou dispositivo físico

## 🧪 Rodando os testes
Todos os testes unitários
```bash
./gradlew test
```

Testes instrumentados

```bash
./gradlew connectedAndroidTest
```

Todos os testes de todos os módulos

```bash
./gradlew check
```

## 📄 Licença

Este projeto é apenas para fins de avaliação e estudo.

## ✨ Autor

Desenvolvido por Leandro Araujo
Android Developer | Kotlin | Clean Architecture | Jetpack Compose