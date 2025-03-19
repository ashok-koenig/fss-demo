```toml
name = 'Register'
method = 'POST'
url = 'http://localhost:8080/api/auth/register'
sortWeight = 1000000
id = '51a10249-61d5-436d-92fb-fe24264beaf2'

[body]
type = 'JSON'
raw = '''
{
  username: "test",
  password: "test",
  firstName: "test",
  lastName: "test",
  role: "USER"
}'''
```
