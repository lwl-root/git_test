# node: /lib64/libm.so.6: version 'GLIBC_2.27'not found问题解决方案 

## 场景

centos7服务器使用nvm安装的node之后，只要使用npm或者node，均会出现以下问题。



```shell
npm -v
node: /lib64/libm.so.6: version `GLIBC_2.27' not found (required by node)
node: /lib64/libc.so.6: version `GLIBC_2.25' not found (required by node)
node: /lib64/libc.so.6: version `GLIBC_2.28' not found (required by node)
node: /lib64/libstdc++.so.6: version `CXXABI_1.3.9' not found (required by node)
node: /lib64/libstdc++.so.6: version `GLIBCXX_3.4.20' not found (required by node)
node: /lib64/libstdc++.so.6: version `GLIBCXX_3.4.21' not found (required by node)
```

## 原因

查看系统内安装的glibc版本
然后再根据分析可得知 新版的node v18开始 都需要GLIBC_2.27支持，可是目前系统内却没有那么高的版本



```shell
 strings /lib64/libc.so.6 |grep GLIBC_
GLIBC_2.2.5
...
GLIBC_2.17
....
```

## 解决办法

### 更新glibc

根据提示 安装所需要的glibc-2.28



```shell
wget http://ftp.gnu.org/gnu/glibc/glibc-2.28.tar.gz
tar xf glibc-2.28.tar.gz 
cd glibc-2.28/ && mkdir build  && cd build
../configure --prefix=/usr --disable-profile --enable-add-ons --with-headers=/usr/include --with-binutils=/usr/bin
```

### 可能出现的错误

上步更新glibc 可能会发生错误。
如果没有错误 下边这一步 不用看。

##### make问题



```shell
configure: error: 
*** These critical programs are missing or too old: make bison compiler
*** Check the INSTALL file for required versions.
```

解决办法：升级gcc与make



```shell
# 升级GCC(默认为4 升级为8)</span>
yum install -y centos-release-scl
yum install -y devtoolset-8-gcc*
mv /usr/bin/gcc /usr/bin/gcc-4.8.5
ln -s /opt/rh/devtoolset-8/root/bin/gcc /usr/bin/gcc
mv /usr/bin/g++ /usr/bin/g++-4.8.5
ln -s /opt/rh/devtoolset-8/root/bin/g++ /usr/bin/g++

# 升级 make(默认为3 升级为4)
wget http://ftp.gnu.org/gnu/make/make-4.3.tar.gz
tar -xzvf make-4.3.tar.gz && cd make-4.3/
./configure  --prefix=/usr/local/make
make && make install
cd /usr/bin/ && mv make make.bak
ln -sv /usr/local/make/bin/make /usr/bin/make
```

这时 所有的问题 都已经解决完毕 再重新执行上一步 更新glibc即可



```shell
cd /root/glibc-2.28/build
../configure --prefix=/usr --disable-profile --enable-add-ons --with-headers=/usr/include --with-binutils=/usr/bin
```

##### 我的依旧报错：bison太老旧



```shell
configure: error: 
*** These critical programs are missing or too old: bison
*** Check the INSTALL file for required versions.
```

看看我的bison版本多少



```shell
bison -v
-bash: bison: 未找到命令
```

这时 所有的问题 真的真的都已经解决完毕 再重新执行上一步 更新glibc即可



```shell
cd /root/glibc-2.28/build
../configure --prefix=/usr --disable-profile --enable-add-ons --with-headers=/usr/include --with-binutils=/usr/bin

yum install -y bison
```

## 继续更新

make 和 make install在linux中就是安装软件的意思 简单这么理解就好。
这个过程较长，大约半小时左右，建议打一局游戏就好了。



```shell
make && make install
```

验证下 是不是好了



```shell
npm -v
```

如果还是出现下面的问题，要连接新的动态库



```shell
npm -v
node: /lib64/libstdc++.so.6: version `CXXABI_1.3.9' not found (required by node)
node: /lib64/libstdc++.so.6: version `GLIBCXX_3.4.20' not found (required by node)
node: /lib64/libstdc++.so.6: version `GLIBCXX_3.4.21' not found (required by node)
```

用下面命令查看



```shell
strings /usr/lib64/libstdc++.so.6 | grep CXXABI
```

[![img](https://img2024.cnblogs.com/blog/1893152/202403/1893152-20240311203237858-1010753407.png)](https://img2024.cnblogs.com/blog/1893152/202403/1893152-20240311203237858-1010753407.png)

更新libstdc++.so.6.0.26



```shell
# 更新lib libstdc++.so.6.0.26
 
wget https://cdn.frostbelt.cn/software/libstdc%2B%2B.so.6.0.26
 
 
# 替换系统中的/usr/lib64
cp libstdc++.so.6.0.26 /usr/lib64/

cd /usr/lib64/

ln -snf ./libstdc++.so.6.0.26 libstdc++.so.6
```

验证



```shell
npm -v
```