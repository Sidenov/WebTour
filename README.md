[![Build status](https://ci.appveyor.com/api/projects/status/o4vgi93mw7wgumjl?svg=true)](https://ci.appveyor.com/project/Sidenov/webtour)

+ [**Планирование автоматизации**](docs/Plan.md)
+ [**Отчёт о проведенном тестировании**](docs/Report.md)
+ [**Отчёт по итогам автоматизации**](docs/Summary.md)
___
### Необходимое окружение:
- IntelliJ IDEA
- Docker или Docker Toolbox

### Процедура запуска авто-тестов:
1. Клонировать проект
2. Запустить контейнеры командой:
   > **docker-compose up -d**
3. Убедиться что контейнеры запущены командой:
   > **docker ps**
4. Запустить приложение командой:
   > **java -jar artifacts/aqa-shop.jar**
5. Запустить эмулятор банковских карт с каталога gate-simulator:
   > **cd gate-simulator**
   > **npm start**
6. Запустить авто-тесты командой:
   > **./gradlew clean test**
7. Для создания отчета Allure запустить команду:
   > **./gradlew allureServe**


