Feature: Simple test scenarios

  Background:
    Given Stub chatlayer/init_request.json

  Scenario: Return a welcome message
    Given Stub chatlayer/message_welcome.json
    When Request is processed
    Then Response matches /output/welcomeResponse.json

