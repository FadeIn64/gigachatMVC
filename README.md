# gigachatMVC

Приложение GigachatMVC было специально разработано для хакатона ROWI.TECH командой Гренадёры

О приложении:
  gigachatMVC это приложение реализующие общение между двумя ролями пользователями;
  Мэнэджеры: 
      Мэнэджэры делятся дополнительно на три подроли (TOPIC_COMMON, TOPIC_DEPOSIT, CREDIT);
      Каждая из подкатегории:
        может работать только в тех чатах, которые совпадают с TOPIC_* ролью мэнеджера;
        может переопределить тему сообщения;
        может завершить общение с пользователем;
  Клиенты:
      Клиенты полностью видят все своые рание общение;
      Когда Мэнеджер закрывает чат, то выбсокет клиент пользователя завершается;
  Авторизация:
      Авторизация выполнена по стандарту Auth2.0 через Keycloaker;
      
  Комментарии команды:
    Изначально наше приложение изначально планировалось под REST API архитекуру, однако команда столкнулась при переходе с basic фунтификации на keycloak с CORS ошибкой, поэтому было решено перейти на Spring MVC архитектуру используя Thymeleaf. Из-за смены архитектуры было сложно перенести возможности ранне реализованные через REST (собенно, когда на дворее 3 часа ночи). Не до конца была реализованна идея комнаты ожидания для мэнеджера, которая заменяла Список доступных чатов для мэнеджеров, переосмысление этого пункта частично было так назыывемым дополнительным функионалам. Однако если для Мэнеджера не находится чатов он должен пытаться постоянно стучаться к чату, пока какому-то пользователю не понадобится помощь по тематике Мэнеджера. Также не было реализованно возможности подключения бота к чату вместо мэнеджера первого звена(TOPIC_COMMON) не было реализовано как простого шаблона, так иотправку через брокера сообщений на сервер заглушку данных которые понадобелись для обучения AI. Также из-за скорого перехода остался только тестовый дизайн.
    Командой были сделаны выводы по ситуации с нереализованным функционалом. Мы считаем что поздно приняли решение на переход MVC архитекуру, из-за слишком сфокусированного внимания на CORS ошибке.

Проверка перед запуском приложения:
  должны быть запущены докер контейнеры Postgres 5432:5432 и KeyCloak 9090:9090 :
    Наша команда развернула докер контейнеры по гайду, предоставленным экспертами для хакатона;
  keycloak что должно быть:
    client с Access Type = confidential
    Должны быть созданы роли: USER, MANAGER, TOPIC_COMMON, TOPIC_DEPOSIT, TOPIC_CREDIT
    Советуем вам для проверки созать 4 пользователей с 1-ой User ролью, с 3-мя MANAGER ролью и распределить TOPIC_COMMON, TOPIC_DEPOSIT, TOPIC_CREDIT для каждего пользователя с  ролью MANAGER;
    
    
Запуск приложения
  applications.properties:
    параметр spring.jpa.hibernate.ddl-auto= update надо заменить на create
    keycloak конфигурация должна быть настроена под вами созданный клиент с условиями указаннами в [Проверка перед запуском приложенияю].[keycloak что должно быть];
  Нюансы с БД:
    GigachatMVC использует h2DB с подключением к локальному файлу, Гитхаб не очень любит .db файлы поэтому для подстраховки будут отправлены на почту два .db файла;
    ./src/db - папка для файлов БД;
    Перед запуском приложения нужно убелиться что к базе нет каких-либо подключений к БД;
  Запуск:
    Приложение нужно запустить из класса GigachatMVCApplication.java;
    Входная точка приложения  localhost:8080/;
    logout: localhost:8080/;
    советуем использовать основное окно браузера для USER и окно инкогнито для MANAGER;
    у приложения реализован логаут так что можно спокойно ходить по пользователям;
  
