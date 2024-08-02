在将 Oracle 12c 的 DMP 文件导入到 Oracle 19c 数据库时，如果需要修改用户名，可以使用传统的 `imp` 和 `exp` 工具，结合 `FROMUSER` 和 `TOUSER` 参数来实现。这些参数允许你在导入时将数据从一个用户导入到另一个用户。

以下是详细的步骤：

### 步骤 1：从 Oracle 12c 导出数据

1. **在 Oracle 12c 数据库中使用 `exp` 工具导出数据**。

   打开终端或命令提示符，执行以下命令：

   ```sh
   exp your_user/your_password@your_service_name file=your_dump_file.dmp log=export.log
   ```

   - `your_user`：源数据库用户名。
   - `your_password`：源数据库用户密码。
   - `your_service_name`：Oracle 服务名。
   - `your_dump_file.dmp`：导出的 DMP 文件名。
   - `export.log`：导出日志文件名。

### 步骤 2：将 DMP 文件传输到目标服务器

使用 SCP、FTP 或其他文件传输工具将 `your_dump_file.dmp` 文件传输到 Oracle 19c 服务器上的一个目录中。

### 步骤 3：在 Oracle 19c 中导入数据并修改用户名

1. **确保目标用户存在**：在 Oracle 19c 数据库中创建目标用户（如果尚未创建）。

   ```sql
   CREATE USER target_user IDENTIFIED BY target_password;
   GRANT CONNECT, RESOURCE TO target_user;
   ```

2. **使用 `imp` 工具导入数据，并将数据从源用户导入到目标用户**。

   打开终端或命令提示符，执行以下命令：

   ```sh
   imp target_user/target_password@your_service_name file=your_dump_file.dmp log=import.log FROMUSER=your_user TOUSER=target_user
   ```

   - `target_user`：目标数据库用户名。
   - `target_password`：目标数据库用户密码。
   - `your_service_name`：Oracle 服务名。
   - `your_dump_file.dmp`：要导入的 DMP 文件名。
   - `import.log`：导入日志文件名。
   - `FROMUSER`：源数据库用户名。
   - `TOUSER`：目标数据库用户名。

### 示例操作步骤

假设以下信息：
- 导出的 DMP 文件名：`export.dmp`
- 源用户名：`source_user`
- 源用户密码：`source_password`
- 目标用户名：`target_user`
- 目标用户密码：`target_password`
- 源数据库服务名：`source_service`
- 目标数据库服务名：`target_service`

#### 步骤 1：在 Oracle 12c 中导出数据

```sh
exp source_user/source_password@source_service file=export.dmp log=export.log
```

#### 步骤 2：将 DMP 文件传输到目标服务器

使用 SCP 或其他文件传输工具将 `export.dmp` 文件传输到目标 Oracle 19c 服务器的某个目录中。

#### 步骤 3：在 Oracle 19c 中创建目标用户

```sql
-- 连接到目标数据库
sqlplus sys/your_sys_password@target_service AS SYSDBA

-- 创建目标用户
CREATE USER target_user IDENTIFIED BY target_password;
GRANT CONNECT, RESOURCE TO target_user;
```

#### 步骤 4：在 Oracle 19c 中导入数据

```sh
imp target_user/target_password@target_service file=export.dmp log=import.log FROMUSER=source_user TOUSER=target_user
```

### 注意事项

1. **字符集**：确保源数据库和目标数据库的字符集兼容。
2. **权限**：确保你有足够的权限执行导入和导出操作。
3. **依赖关系**：检查和处理可能的依赖关系和外键约束，确保数据完整性。
4. **导入选项**：根据需要，可以使用更多的 `imp` 选项来控制导入过程。

通过这些步骤，你可以将 Oracle 12c 的 DMP 文件导入到 Oracle 19c 数据库中，并在导入过程中修改用户名。如果遇到问题，可以查看 `export.log` 和 `import.log` 日志文件以获取详细的错误信息，并根据错误提示进行调整和修正。