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

* Check validity of a token
```
POST {host}/oauth/check_token?token={token}
Content-Type: application/json
```
