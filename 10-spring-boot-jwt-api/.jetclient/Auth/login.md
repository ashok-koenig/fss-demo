```toml
name = 'login'
method = 'POST'
url = 'http://localhost:8080/api/auth/login'
sortWeight = 2000000
id = 'abd10488-d819-44c4-9c99-171aa3a3564f'

[body]
type = 'JSON'
raw = '''
{
  username: "admin",
  password: "admin"
}'''
```
