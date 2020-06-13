# Authentication service

### Use 

* Get a token
```
POST {host}/oauth/token
Content-Type: application/x-www-form-urlencoded
Authorization: Basic {clientId} {clientSecret}

password={password}&
username={username}&
grant_type=password
```
RETURN
```
{
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyMkBnbWFpbC5jb20iLCJzY29wZSI6WyJhbGwiXSwic3Vic2NyaXB0aW9uIjoiUFJPIiwiZXhwIjoxNTkyMTE0Mjk1LCJ1c2VySWQiOjEsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiIyZGRjNTgyOS1lNDJmLTRmMDItYTA1NC0wMDBhNDkwNTFkMTIiLCJjbGllbnRfaWQiOiJ0cmNrbHN0In0.3BR_KVPSb1znlhtrB8UKhKB01aIdVlGRheT_WUVQ2XI",
  "token_type": "bearer",
  "expires_in": 43193,
  "scope": "all",
  "subscription": "PRO",
  "userId": 1,
  "jti": "2ddc5829-e42f-4f02-a054-000a49051d12"
}
```
* Check validity of a token
```
POST {host}/oauth/check_token?token={token}
Content-Type: application/json
```
