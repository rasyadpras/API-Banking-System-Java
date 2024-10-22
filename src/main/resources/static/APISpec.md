
## API Reference

### Authentication

#### Register

Request:

- Method: POST
- Endpoint: `/api/v1/auth/register`
- Header:
  - Content-Type: application/json
  - Accept: application/json
- Body:

  - Gender: `male` or `female`
  - Identity Type: `id card | ktp | passport | driver lisence | student card | other`

```json
{
  "email": "string",
  "password": "string",
  "fullName": "string",
  "gender": "string",
  "birthDate": "string",
  "identityType": "string",
  "identityNumber": "string",
  "address": "string",
  "city": "string",
  "province": "string",
  "country": "string",
  "phoneNumber": "string"
}
```

Response:

- Roles: `ADMINISTRATOR | OFFICER | CUSTOMER`
- Gender: `MALE | FEMALE`
- Identity Type: `IDENTITY_CARD | PASSPORT | DRIVER_LICENSE | STUDENT_CARD | OTHER`

```json
{
  "statusCode": 201,
  "message": "Created",
  "data": {
    "userId": "string",
    "email": "string",
    "roles": [
      "string"
    ],
    "profile": {
      "profileId": "string",
      "fullName": "string",
      "gender": "string",
      "birthDate": "localDate",
      "identityType": "string",
      "identityNumber": "string",
      "address": "string",
      "city": "string",
      "province": "string",
      "country": "string",
      "phoneNumber": "string"
    }
  }
}
```

#### Login

Request:

- Method: POST
- Endpoint: `/api/v1/auth/login`
- Header:
  - Content-Type: application/json
  - Accept: application/json
- Body:

```json
{
  "email": "string",
  "password": "string"
}
```

Response:

- Roles: `ADMINISTRATOR | OFFICER | CUSTOMER`

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": {
    "email": "string",
    "token": "string",
    "roles": [
      "string"
    ]
  }
}
```

#### Add Role

Request:

- Method: PATCH
- Endpoint: `/api/v1/auth/role/{id}`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Content-Type: application/json
  - Accept: application/json
- Body:

  - Roles: `admin` or `staff`

```json
{
  "role": "string"
}
```

Response:

- Roles: `ADMINISTRATOR | OFFICER | CUSTOMER`
- User Account Status: `ACTIVE | NOT_VERIFIED | LOCKED`
- Gender: `MALE | FEMALE`

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": {
    "id": "string",
    "email": "string",
    "roles": [
      "string"
    ],
    "status": "string",
    "profile": {
      "profileId": "string",
      "fullName": "string",
      "gender": "string"
    },
    "createdAt": "localDateTime",
    "updatedAt": "localDateTime"
  }
}
```

#### Forgot Password

Request:

- Method: PATCH
- Endpoint: `/api/v1/auth/forgot-password/{id}`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Content-Type: application/json
  - Accept: application/json
- Body:

```json
{
  "password": "string"
}
```

Response:

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": "Password has been changed"
}
```

#### Reset Password

Request:

- Method: PATCH
- Endpoint: `/api/v1/auth/reset-password/{id}`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Content-Type: application/json
  - Accept: application/json
- Body:

```json
{
  "oldPassword": "string",
  "newPassword": "string"
}
```

Response:

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": "Password has been changed"
}
```

#### Verify Account

Request:

- Method: PATCH
- Endpoint: `/api/v1/auth/verify/{id}`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Accept: application/json

Response:

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": "Account has been verified"
}
```

#### Unlock Account

Request:

- Method: PATCH
- Endpoint: `/api/v1/auth/unlock/{id}`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Accept: application/json

Response:

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": "Account has been unlocked"
}
```

### Bank Account

#### Create Bank Account

Request:

- Method: POST
- Endpoint: `/api/v1/bank-accounts`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Content-Type: application/json
  - Accept: application/json
- Body:

  - Type: `regular | business | student | plan | other`

```json
{
  "branchId": "string",
  "profileId": "string",
  "type": "string"
}
```

Response:

  - Bank Account Number: `{branchCode} + {hashedMonthYear} + {sequenceNumber}`
  - Gender: `MALE | FEMALE`
  - Identity Type: `IDENTITY_CARD | PASSPORT | DRIVER_LICENSE | STUDENT_CARD | OTHER`
  - Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`
  - Bank Account Status: `ACTIVE | CLOSED`
  - Card Type: `DEBIT | CREDIT`
  - Card Principal: `VISA | MASTERCARD | GPN | JCB | UNION_PAY | AMERICAN_EXPRESS | NO_PRINCIPAL | OTHER`
  - Card Status: `ACTIVE | BLOCKED | EXPIRED`

```json
{
  "statusCode": 201,
  "message": "Created",
  "data": {
    "id": "string",
    "branch": {
      "branchId": "string",
      "code": "string",
      "branchName": "string",
      "region": "string",
      "address": "string"
    },
    "profile": {
      "profileId": "string",
      "fullName": "string",
      "gender": "string",
      "birthDate": "string",
      "identityType": "string",
      "identityNumber": "string",
      "address": "string",
      "city": "string",
      "province": "string",
      "country": "string",
      "phoneNumber": "string",
      "user": {
        "userId": "string",
        "email": "string",
        "createdAt": "localDateTime",
        "updatedAt": "localDateTime"
      },
      "createdAt": "localDateTime",
      "updatedAt": "localDateTime"
    },
    "accountNumber": "string",
    "bankAccountType": "string",
    "balance": "bigDecimal",
    "bankAccountStatus": "string",
    "cards": [
      {
        "cardId": "string",
        "cardType": "string",
        "cardNumber": "string",
        "principal": "string",
        "validThru": "localDate",
        "cardStatus": "string"
      }
    ],
    "createdAt": "localDateTime",
    "updatedAt": "localDateTime"
  }
}
```

#### Get Bank Account by ID

Request:

- Method: GET
- Endpoint: `/api/v1/bank-accounts/{id}`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Accept: application/json

Response:

  - Bank Account Number: `{branchCode} + {hashedMonthYear} + {sequenceNumber}`
  - Gender: `MALE | FEMALE`
  - Identity Type: `IDENTITY_CARD | PASSPORT | DRIVER_LICENSE | STUDENT_CARD | OTHER`
  - Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`
  - Bank Account Status: `ACTIVE | CLOSED`
  - Card Type: `DEBIT | CREDIT`
  - Card Principal: `VISA | MASTERCARD | GPN | JCB | UNION_PAY | AMERICAN_EXPRESS | NO_PRINCIPAL | OTHER`
  - Card Status: `ACTIVE | BLOCKED | EXPIRED`

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": {
    "id": "string",
    "branch": {
      "branchId": "string",
      "code": "string",
      "branchName": "string",
      "region": "string",
      "address": "string"
    },
    "profile": {
      "profileId": "string",
      "fullName": "string",
      "gender": "string",
      "birthDate": "string",
      "identityType": "string",
      "identityNumber": "string",
      "address": "string",
      "city": "string",
      "province": "string",
      "country": "string",
      "phoneNumber": "string",
      "user": {
        "userId": "string",
        "email": "string",
        "createdAt": "localDateTime",
        "updatedAt": "localDateTime"
      },
      "createdAt": "localDateTime",
      "updatedAt": "localDateTime"
    },
    "accountNumber": "string",
    "bankAccountType": "string",
    "balance": "bigDecimal",
    "bankAccountStatus": "string",
    "cards": [
      {
        "cardId": "string",
        "cardType": "string",
        "cardNumber": "string",
        "principal": "string",
        "validThru": "localDate",
        "cardStatus": "string"
      }
    ],
    "createdAt": "localDateTime",
    "updatedAt": "localDateTime"
  }
}
```

#### Close Bank Account

Request:

- Method: PATCH
- Endpoint: `/api/v1/bank-accounts/{id}/close`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Accept: application/json

Response:

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": "Account closed"
}
```

### Branch

#### Create Branch

Request:

- Method: POST
- Endpoint: `/api/v1/branches`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Content-Type: application/json
  - Accept: application/json
- Body:

```json
{
  "branchCode": "string",
  "branchName": "string",
  "region": "string",
  "address": "string"
}
```

Response:

```json
{
  "statusCode": 201,
  "message": "Created",
  "data": {
    "branchId": "string",
    "code": "string",
    "branchName": "string",
    "region": "string",
    "address": "string",
    "createdAt": "localDateTime",
    "updatedAt": "localDateTime"
  }
}
```

#### Get All Branches

Request:

- Method: GET
- Endpoint: `/api/v1/branches`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Accept: application/json
- Query Param:
  - region: string `optional`,

Response:

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": [
    {
      "branchId": "string",
      "code": "string",
      "branchName": "string",
      "region": "string",
      "address": "string",
      "createdAt": "localDateTime",
      "updatedAt": "localDateTime"
    }
  ]
}
```

#### Update Branch

Request:

- Method: PATCH
- Endpoint: `/api/v1/branches/{id}`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Content-Type: application/json
  - Accept: application/json
- Body:

```json
{
  "branchName": "string",
  "address": "string"
}
```

Response:

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": {
    "branchId": "string",
    "code": "string",
    "branchName": "string",
    "region": "string",
    "address": "string",
    "createdAt": "localDateTime",
    "updatedAt": "localDateTime"
  }
}
```

### Card

#### Add Card

Request:

- Method: POST
- Endpoint: `/api/v1/cards`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Content-Type: application/json
  - Accept: application/json
- Body:

  - Card Type: `debit` or `credit`
  - Principal Card: `visa | mastercard | gpn | jcb | union | amex | none | other`
  - Expiration Date Format: `MM-yy`

```json
{
  "bankAccountId": "string",
  "cardType": "string",
  "cardNumber": "string",
  "principalCard": "string",
  "expirationDate": "localDate",
  "cvv": "string"
}
```

Response:

- Gender: `MALE | FEMALE`
- Identity Type: `IDENTITY_CARD | PASSPORT | DRIVER_LICENSE | STUDENT_CARD | OTHER`
- Bank Account Number: `{branchCode} + {hashedMonthYear} + {sequenceNumber}`
- Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`
- Bank Account Status: `ACTIVE | CLOSED`
- Card Type: `DEBIT | CREDIT`
- Card Principal: `VISA | MASTERCARD | GPN | JCB | UNION_PAY | AMERICAN_EXPRESS | NO_PRINCIPAL | OTHER`
- Card Status: `ACTIVE | BLOCKED | EXPIRED`

```json
{
  "statusCode": 201,
  "message": "Created",
  "data": {
    "id": "string",
    "bankAccount": {
      "bankAccountId": "string",
      "profile": {
        "profileId": "string",
        "fullName": "string",
        "gender": "string",
        "birthDate": "localDate",
        "identityType": "string",
        "identityNumber": "string"
      },
      "accountNumber": "string",
      "bankAccountType": "string",
      "bankAccountStatus": "string",
      "createdAt": "localDateTime",
      "updatedAt": "localDateTime"
    },
    "cardType": "string",
    "cardNumber": "string",
    "principal": "string",
    "validThru": "localDate",
    "cvv": "string",
    "cardStatus": "string",
    "activeDate": "localDateTime",
    "updatedAt": "localDateTime"
  }
}
```

#### Get Card by ID

Request:

- Method: GET
- Endpoint: `/api/v1/cards/{id}`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Accept: application/json

Response:

- Gender: `MALE | FEMALE`
- Identity Type: `IDENTITY_CARD | PASSPORT | DRIVER_LICENSE | STUDENT_CARD | OTHER`
- Bank Account Number: `{branchCode} + {hashedMonthYear} + {sequenceNumber}`
- Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`
- Bank Account Status: `ACTIVE | CLOSED`
- Card Type: `DEBIT | CREDIT`
- Card Principal: `VISA | MASTERCARD | GPN | JCB | UNION_PAY | AMERICAN_EXPRESS | NO_PRINCIPAL | OTHER`
- Card Status: `ACTIVE | BLOCKED | EXPIRED`

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": {
    "id": "string",
    "bankAccount": {
      "bankAccountId": "string",
      "profile": {
        "profileId": "string",
        "fullName": "string",
        "gender": "string",
        "birthDate": "localDate",
        "identityType": "string",
        "identityNumber": "string"
      },
      "accountNumber": "string",
      "bankAccountType": "string",
      "bankAccountStatus": "string",
      "createdAt": "localDateTime",
      "updatedAt": "localDateTime"
    },
    "cardType": "string",
    "cardNumber": "string",
    "principal": "string",
    "validThru": "localDate",
    "cvv": "string",
    "cardStatus": "string",
    "activeDate": "localDateTime",
    "updatedAt": "localDateTime"
  }
}
```

#### Unblock Card

Request:

- Method: PATCH
- Endpoint: `/api/v1/cards/{id}/unblock`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Accept: application/json

Response:

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": "Card has been activated"
}
```

### Cash Transaction

#### Create Cash Deposit

Request:

- Method: POST
- Endpoint: `/api/v1/transactions/cash-flow/deposit`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Content-Type: application/json
  - Accept: application/json
- Body:

```json
{
  "accountNumber": "string",
  "amount": "number"
}
```

Response:

- Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`

```json
{
  "statusCode": 201,
  "message": "Created",
  "data": {
    "id": "string",
    "bankAccount": {
      "accountId": "string",
      "profile": {
        "profileId": "string",
        "fullName": "string"
      },
      "accountNumber": "string",
      "bankAccountType": "string"
    },
    "transactionType": "DEPOSIT",
    "amount": "bigDecimal",
    "transactionDate": "localDateTime"
  }
}
```

#### Create Cash Withdraw

Request:

- Method: POST
- Endpoint: `/api/v1/transactions/cash-flow/withdraw`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Content-Type: application/json
  - Accept: application/json
- Body:

```json
{
  "accountNumber": "string",
  "amount": "number"
}
```

Response:

- Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`

```json
{
  "statusCode": 201,
  "message": "Created",
  "data": {
    "id": "string",
    "bankAccount": {
      "accountId": "string",
      "profile": {
        "profileId": "string",
        "fullName": "string"
      },
      "accountNumber": "string",
      "bankAccountType": "string"
    },
    "transactionType": "WITHDRAWAL",
    "amount": "bigDecimal",
    "transactionDate": "localDateTime"
  }
}
```

#### Get Transaction by ID

Request:

- Method: GET
- Endpoint: `/api/v1/transactions/cash-flow/{id}`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Accept: application/json

Response:

- Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`
- Transaction Type: `DEPOSIT | WITHDRAWAL`

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": {
    "id": "string",
    "bankAccount": {
      "accountId": "string",
      "profile": {
        "profileId": "string",
        "fullName": "string"
      },
      "accountNumber": "string",
      "bankAccountType": "string"
    },
    "transactionType": "string",
    "amount": "bigDecimal",
    "transactionDate": "localDateTime"
  }
}
```

#### Get All Transactions by User

Request:

- Method: GET
- Endpoint: `/api/v1/transactions/{bankAccId}/cash-flow`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Accept: application/json

Response:

- Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`
- Transaction Type: `DEPOSIT | WITHDRAWAL`

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": [
    {
      "id": "string",
      "bankAccount": {
        "accountId": "string",
        "profile": {
          "profileId": "string",
          "fullName": "string"
        },
        "accountNumber": "string",
        "bankAccountType": "string"
      },
      "transactionType": "string",
      "amount": "bigDecimal",
      "transactionDate": "localDateTime"
    }
  ]
}
```

### Profile

#### Get All Profiles

Request:

- Method: GET
- Endpoint: `/api/v1/profiles`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Accept: application/json
- Query Param:
  - city: string `optional`,

Response:

- Bank Account Number: `{branchCode} + {hashedMonthYear} + {sequenceNumber}`
- Gender: `MALE | FEMALE`
- Identity Type: `IDENTITY_CARD | PASSPORT | DRIVER_LICENSE | STUDENT_CARD | OTHER`
- User Account Status: `ACTIVE | NOT_VERIFIED | LOCKED`
- Roles: `ADMINISTRATOR | OFFICER | CUSTOMER`
- Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`
- Bank Account Status: `ACTIVE | CLOSED`
- Card Type: `DEBIT | CREDIT`
- Card Principal: `VISA | MASTERCARD | GPN | JCB | UNION_PAY | AMERICAN_EXPRESS | NO_PRINCIPAL | OTHER`
- Card Status: `ACTIVE | BLOCKED | EXPIRED`

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": [
    {
      "id": "string",
      "fullName": "string",
      "gender": "string",
      "birthDate": "localDate",
      "identityType": "string",
      "identityNumber": "string",
      "address": "string",
      "city": "string",
      "province": "string",
      "country": "string",
      "phoneNumber": "string",
      "user": {
        "userId": "string",
        "email": "string",
        "accountUserStatus": "string",
        "roles": [
          "string"
        ],
        "createdAt": "localDateTime",
        "updatedAt": "localDateTime"
      },
      "bankAccounts": [
        {
          "accountId": "string",
          "branch": {
            "branchId": "string",
            "code": "string",
            "branchName": "string",
            "region": "string",
            "address": "string"
          },
          "accountNumber": "string",
          "bankAccountType": "string",
          "bankAccountStatus": "string",
          "cards": [
            {
              "cardId": "string",
              "cardType": "string",
              "cardNumber": "string",
              "principal": "string",
              "cardStatus": "string"
            }
          ],
          "createdAt": "localDateTime",
          "updatedAt": "localDateTime"
        }
      ],
      "createdAt": "localDateTime",
      "updatedAt": "localDateTime"
    }
  ]
}
```

#### Get Profile by ID

Request:

- Method: GET
- Endpoint: `/api/v1/profiles/{id}`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Accept: application/json

Response:

- Bank Account Number: `{branchCode} + {hashedMonthYear} + {sequenceNumber}`
- Gender: `MALE | FEMALE`
- Identity Type: `IDENTITY_CARD | PASSPORT | DRIVER_LICENSE | STUDENT_CARD | OTHER`
- User Account Status: `ACTIVE | NOT_VERIFIED | LOCKED`
- Roles: `ADMINISTRATOR | OFFICER | CUSTOMER`
- Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`
- Bank Account Status: `ACTIVE | CLOSED`
- Card Type: `DEBIT | CREDIT`
- Card Principal: `VISA | MASTERCARD | GPN | JCB | UNION_PAY | AMERICAN_EXPRESS | NO_PRINCIPAL | OTHER`
- Card Status: `ACTIVE | BLOCKED | EXPIRED`

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": {
    "id": "string",
    "fullName": "string",
    "gender": "string",
    "birthDate": "localDate",
    "identityType": "string",
    "identityNumber": "string",
    "address": "string",
    "city": "string",
    "province": "string",
    "country": "string",
    "phoneNumber": "string",
    "user": {
      "userId": "string",
      "email": "string",
      "accountUserStatus": "string",
      "roles": [
        "string"
      ],
      "createdAt": "localDateTime",
      "updatedAt": "localDateTime"
    },
    "bankAccounts": [
      {
        "accountId": "string",
        "branch": {
          "branchId": "string",
          "code": "string",
          "branchName": "string",
          "region": "string",
          "address": "string"
        },
        "accountNumber": "string",
        "bankAccountType": "string",
        "bankAccountStatus": "string",
        "cards": [
          {
            "cardId": "string",
            "cardType": "string",
            "cardNumber": "string",
            "principal": "string",
            "cardStatus": "string"
          }
        ],
        "createdAt": "localDateTime",
        "updatedAt": "localDateTime"
      }
    ],
    "createdAt": "localDateTime",
    "updatedAt": "localDateTime"
  }
}
```

#### Update Profile

Request:

- Method: PUT
- Endpoint: `/api/v1/profiles/{id}`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Content-Type: application/json
  - Accept: application/json
- Body:

  - Gender: `male or female`
  - Identity Type: `id card | ktp | passport | driver lisence | student card | other`

```json
{
  "fullName": "string",
  "gender": "string",
  "birthDate": "localDate",
  "identityType": "string",
  "identityNumber": "string",
  "address": "string",
  "city": "string",
  "province": "string",
  "country": "string",
  "phoneNumber": "string"
}
```

Response:

- Bank Account Number: `{branchCode} + {hashedMonthYear} + {sequenceNumber}`
- Gender: `MALE | FEMALE`
- Identity Type: `IDENTITY_CARD | PASSPORT | DRIVER_LICENSE | STUDENT_CARD | OTHER`
- User Account Status: `ACTIVE | NOT_VERIFIED | LOCKED`
- Roles: `ADMINISTRATOR | OFFICER | CUSTOMER`
- Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`
- Bank Account Status: `ACTIVE | CLOSED`
- Card Type: `DEBIT | CREDIT`
- Card Principal: `VISA | MASTERCARD | GPN | JCB | UNION_PAY | AMERICAN_EXPRESS | NO_PRINCIPAL | OTHER`
- Card Status: `ACTIVE | BLOCKED | EXPIRED`

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": {
    "id": "string",
    "fullName": "string",
    "gender": "string",
    "birthDate": "localDate",
    "identityType": "string",
    "identityNumber": "string",
    "address": "string",
    "city": "string",
    "province": "string",
    "country": "string",
    "phoneNumber": "string",
    "user": {
      "userId": "string",
      "email": "string",
      "accountUserStatus": "string",
      "roles": [
        "string"
      ],
      "createdAt": "localDateTime",
      "updatedAt": "localDateTime"
    },
    "bankAccounts": [
      {
        "accountId": "string",
        "branch": {
          "branchId": "string",
          "code": "string",
          "branchName": "string",
          "region": "string",
          "address": "string"
        },
        "accountNumber": "string",
        "bankAccountType": "string",
        "bankAccountStatus": "string",
        "cards": [
          {
            "cardId": "string",
            "cardType": "string",
            "cardNumber": "string",
            "principal": "string",
            "cardStatus": "string"
          }
        ],
        "createdAt": "localDateTime",
        "updatedAt": "localDateTime"
      }
    ],
    "createdAt": "localDateTime",
    "updatedAt": "localDateTime"
  }
}
```

### Transfer

#### Create Transfer Transaction

Request:

- Method: POST
- Endpoint: `/api/v1/transactions/transfer`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Content-Type: application/json
  - Accept: application/json
- Body:

```json
{
  "sourceAccountNumber": "string",
  "destinationAccountNumber": "string",
  "amount": "number"
}
```

Response:

- Bank Account Number: `{branchCode} + {hashedMonthYear} + {sequenceNumber}`
- Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`

```json
{
  "statusCode": 201,
  "message": "Created",
  "data": {
    "id": "string",
    "sourceAccount": {
      "accountId": "string",
      "profile": {
        "profileId": "string",
        "fullName": "string"
      },
      "accountNumber": "string",
      "bankAccountType": "string"
    },
    "destinationAccount": {
      "accountId": "string",
      "profile": {
        "profileId": "string",
        "fullName": "string"
      },
      "accountNumber": "string",
      "bankAccountType": "string"
    },
    "amount": "number",
    "transactionDate": "string"
  }
}
```

#### Get Transfer by ID

Request:

- Method: GET
- Endpoint: `/api/v1/transactions/transfer/{id}`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Accept: application/json

Response:

- Bank Account Number: `{branchCode} + {hashedMonthYear} + {sequenceNumber}`
- Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": {
    "id": "string",
    "sourceAccount": {
      "accountId": "string",
      "profile": {
        "profileId": "string",
        "fullName": "string"
      },
      "accountNumber": "string",
      "bankAccountType": "string"
    },
    "destinationAccount": {
      "accountId": "string",
      "profile": {
        "profileId": "string",
        "fullName": "string"
      },
      "accountNumber": "string",
      "bankAccountType": "string"
    },
    "amount": "number",
    "transactionDate": "string"
  }
}
```

#### Get All Transfers by User

Request:

- Method: GET
- Endpoint: `/api/v1/transactions/{bankAccId}/transfer`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Accept: application/json

Response:

- Bank Account Number: `{branchCode} + {hashedMonthYear} + {sequenceNumber}`
- Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": [
    {
      "id": "string",
      "sourceAccount": {
        "accountId": "string",
        "profile": {
          "profileId": "string",
          "fullName": "string"
        },
        "accountNumber": "string",
        "bankAccountType": "string"
      },
      "destinationAccount": {
        "accountId": "string",
        "profile": {
          "profileId": "string",
          "fullName": "string"
        },
        "accountNumber": "string",
        "bankAccountType": "string"
      },
      "amount": "number",
      "transactionDate": "string"
    }
  ]
}
```

#### Get All Transfers by Sender

Request:

- Method: GET
- Endpoint: `/api/v1/transactions/{bankAccId}/transfer/out`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Accept: application/json

Response:

- Bank Account Number: `{branchCode} + {hashedMonthYear} + {sequenceNumber}`
- Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": [
    {
      "id": "string",
      "sourceAccount": {
        "accountId": "string",
        "profile": {
          "profileId": "string",
          "fullName": "string"
        },
        "accountNumber": "string",
        "bankAccountType": "string"
      },
      "destinationAccount": {
        "accountId": "string",
        "profile": {
          "profileId": "string",
          "fullName": "string"
        },
        "accountNumber": "string",
        "bankAccountType": "string"
      },
      "amount": "number",
      "transactionDate": "string"
    }
  ]
}
```

#### Get All Transfers by Receiver

Request:

- Method: GET
- Endpoint: `/api/v1/transactions/{bankAccId}/transfer/in`
- Header:
  - Authentication: Bearer `<JWT Token>`
  - Accept: application/json

Response:

- Bank Account Number: `{branchCode} + {hashedMonthYear} + {sequenceNumber}`
- Bank Account Type: `REGULAR_SAVINGS | BUSINESS_SAVINGS | STUDENT_SAVINGS | SAVINGS_PLAN | OTHER_SAVINGS`

```json
{
  "statusCode": 200,
  "message": "OK",
  "data": [
    {
      "id": "string",
      "sourceAccount": {
        "accountId": "string",
        "profile": {
          "profileId": "string",
          "fullName": "string"
        },
        "accountNumber": "string",
        "bankAccountType": "string"
      },
      "destinationAccount": {
        "accountId": "string",
        "profile": {
          "profileId": "string",
          "fullName": "string"
        },
        "accountNumber": "string",
        "bankAccountType": "string"
      },
      "amount": "number",
      "transactionDate": "string"
    }
  ]
}
```