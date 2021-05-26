# JavaAppiumAutomation

_Это учебный проект по курсу "Автоматизатор мобильных приложений"_

_Связанный проект: [LissaRider/JavaAppiumAutomation](https://github.com/LissaRider/JavaAppiumAutomation)_

## Библиотеки

| groupId                 | artifactId          | version | description                                    | homepage                                                               |
|:------------------------|:--------------------|:-------:|:-----------------------------------------------|:-----------------------------------------------------------------------|
| io.appium               | java-client         | 6.1.0   | Java client for Appium Mobile Webdriver        | [appium/java-client](https://github.com/appium/java-client)            |
| junit                   | junit               | 4.12    | Unit testing framework for Java                | [junit-team/junit4](https://junit.org/junit4/)                         |

## Приложения для тестирования

#### Расположение (*.apk, *.app)

_src/test/resources/apks_

#### Описание и версии

| name                 | version | description                                   | homepage                                                                             |
|:---------------------|:-------:|:----------------------------------------------|:-------------------------------------------------------------------------------------|
| org.wikipedia.apk    | unknown | Old version up to date 02/18/2018             | [wikipedia](https://play.google.com/store/apps/details?id=org.wikipedia&hl=ru&gl=US) |
| Wikipedia.app        | unknown | Actual official version up to date 05/05/2021 | [wikipedia-ios](https://github.com/wikimedia/wikipedia-ios)                          |

## Работа с jenkins через командную строку (Mac)

[Homebrew Installer](https://www.jenkins.io/download/lts/macos/)

Install the latest LTS version: brew install jenkins-lts

Install a specific LTS version: brew install jenkins-lts@YOUR_VERSION

Start the Jenkins service: brew services start jenkins-lts

Restart the Jenkins service: brew services restart jenkins-lts

Update the Jenkins version: brew upgrade jenkins-lts

## Работа с appium

_Appium - это сервер, написанный на Node.js._

_Его можно собрать и установить из исходного кода или установить непосредственно из NPM._

[Getting Started](http://appium.io/docs/en/about-appium/getting-started/?lang=en)

[appium/appium-desktop](https://github.com/appium/appium-desktop)


#### Установка Node.js

brew install node (Mac с помощью Homebrew)

[node.js](https://nodejs.org/en/download/) (Mac/Windows)


#### Команды appium

npm install -g appium (установка appium, для Windows лучше с --force)

appium & (запуск appium)

kill $(lsof -t -i :4723) (остановка appium на Mac)

#### Установка Scoop на Windows (PowerShell)

Set-ExecutionPolicy RemoteSigned -scope CurrentUser

iwr -useb get.scoop.sh | iex

scoop (для проверки работы)

#### Установка Allure на Windows (cmd)

scoop install allure

allure --version (для проверки работы)

## Лицензия

Copyright © 2021 Lissa Rider