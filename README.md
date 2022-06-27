./gradlew clean test[![Build status](https://ci.appveyor.com/api/projects/status/o4vgi93mw7wgumjl?svg=true)](https://ci.appveyor.com/project/Sidenov/webtour)

+ [**Задание Дипломной работы**](docs/DiplomaTask.md)
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
   > Для Mysql:
   > 
   > **java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar**
   > 
   > Для Postgresql:
   > 
   > **java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar**

   (Если запущен Docker ToolBox, то вместо localhost следует писать IP Toolbox)


5. Запустить авто-тесты командой:
   > Для Mysql:
   > 
   > **./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"**
   > 
   >  Для Postgresql:
   > 
   > **./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"**
6. Для создания отчета Allure запустить команду:
   > **./gradlew allureServe**


