@regression @login
Feature: Login

  @id-002
  Scenario: Add search result picture into new Collection 2.
    Given пользователь заходит на главную страницу
    And пользователь заходит на страницу Войти
    And пользователь входит в систему с учетными данными "oxxxymiron"
    Then пользователь возвращается на главную страницу

  @id-003
  Scenario: Add search result picture into new Collection 3.
    Given пользователь заходит на главную страницу
    And пользователь заходит на страницу Войти
    And пользователь входит в систему с учетными данными "piro2001"
    Then пользователь возвращается на главную страницу
