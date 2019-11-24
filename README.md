# Authentication service

* Use : 
```
POST {host}/oauth/token
Content-Type: application/x-www-form-urlencoded
Authorization: Basic {clientId} {clientSecret}
password={password}&
username={username}&
grant_type=password
```
