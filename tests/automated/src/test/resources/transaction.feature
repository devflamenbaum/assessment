Feature: Transaction Service

  Scenario Outline: Client makes call to POST /transactions successfully
    Given Account Id: <account_id>
    And Remove this account of testing transaction
    And Operation Type Id: <operation_type_id>
    And Amount: "<amount>"

    Then Insert a new account with <document_number>

    When the client calls /transactions
    Then service transaction send status <status>
    And Remove this account of testing transaction
    And Remove test transaction

    Examples:
      | account_id | document_number | operation_type_id | amount    | status |
      | 99         | 12345           | 1                 |  -100.00  | 201    |
      | 199        | 12345           | 2                 |  -78.98   | 201    |
      | 299        | 12345           | 3                 |  -0.99    | 201    |
      | 399        | 12345           | 4                 |  190.00   | 201    |