Feature: Account Service

    Scenario Outline: Client makes call to POST /accounts successfully
        Given a document number <document_number>
        And Remove this <document_number> if exists
        When the client calls /accounts
        Then the client receives status <status>
        And Remove this <document_number> if exists

        Examples:
            | document_number | status |
            | 99999999999     | 201    |
            | 00000000000     | 201    |
            | 11111111111     | 201    |

    Scenario Outline: Client makes call to POST /accounts with already document number in AccountDB
        Given a document number <document_number>
        And Remove this <document_number> if exists
        And Insert this <document_number> in AccountDB
        When the client calls /accounts
        Then the client receives status <status>
        And Remove this <document_number> if exists

        Examples:
            | document_number | status |
            | 99999999999     | 400    |
            | 00000000000     | 400    |
            | 11111111111     | 400    |

    Scenario Outline: Client makes call to GET /accounts/:id
        Given a Account id <account_id>
        Given a document number <document_number>
        And Remove this account
        And Insert this account
        When the client calls /accounts/id
        Then the client receives status <status>
        And Remove this account

        Examples:
            | account_id |document_number| status |
            | 10         | 99999999999   | 200    |
            | 11         | 00000000000   | 200    |
            | 12         | 11111111111   | 200    |
