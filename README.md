# Sqlite_Java
Javaでsqliteにアクセスする。

## Database

### create

```sql
create table DataTable(id text primary key, value text);
```

### test/01_select.db

```sql 
INSERT INTO DataTable(id,value) VALUES ('01','value01');
INSERT INTO DataTable(id,value) VALUES ('02','value02');
INSERT INTO DataTable(id,value) VALUES ('03','value03');
```

## 参考

- [【JDBC ①】JavaからSQLite3のデータベースにアクセスしてみた。:Qiita](https://qiita.com/tsweblabo/items/39bdd73a910417a9a8df)
- [Java SQLiteでinsert/update/deleteするサンプル:ITSakura](https://itsakura.com/java-sqlite-insert)
- [Java SQLiteにJDBC接続してselectするサンプル:ITSakura](https://itsakura.com/java-sqlite-select)