### Linux 主流发行版本
> 我们平常说的 Linux 一般指的是 `Linux内核`，而常见的发行版本Redhat、Ubuntu、Centos 是基于 Linux 内核 开发出来的操作系统与各种应用软件。可以理解为：使用同样的材料制作出不同口味的菜。

### Linux 的目录结构
> Linux 的文件系统是采用层级式的树状目录结构，在此结构中的最上层是根目录"/"。

怎么理解`在 Linux 世界里，一切皆文件`？
Linux 会把 `硬件` 都映射成文件进行管理。

总结：
1. linux 的目录中有且只有一个根目录 / 。
2. linux 的各目录存放的内容是规划好的，不要乱放文件。
3. linux 是以文件的形式管理我们的设备，因此  linux 系统，一切皆为文件。

### shell 与 shell 脚本 与 命令行
- `shell`：Shell 是一个用 C 语言编写的程序（命令解释器），处于内核和用户之间，负责把用户的指令传递给内核并且把执行结果回显给用户。
- `shell 脚本`：是一种用 shell 编写的脚本程序。
- `命令行`：Windows系统接受shell命令的程序是cmd命令行窗口；而Linux发行版ubuntu中对应的程序是terminal终端。

总结：
> shell 是我们与内核的媒介，而平常使用的命令行工具又是我们与 shell命令的媒介。

执行方式：
- 相对路径执行：./xxx.sh
- 绝对路径执行：/xxx/xxx/xxx/xxx.sh

### chmod
![image.png](https://i.loli.net/2020/06/07/g4fQwoPG6lHOex1.png)
chmod也可以用数字来表示权限如：
> chmod 777 file

语法为：
> chmod abc file

其中a,b,c各为一个数字，分别表示User、Group、及Other的权限。
r=4,w=2,x=1
- 若要rwx属性则4+2+1=7
- 若要rw-属性则4+2=6
- 若要r-x属性则4+1=5

### tail 
`tail -400f demo.log `：监控最后 400 行 demo.log 文件的变化。

### grep
`grep -C 5 'foo' file` 显示 file 文件里匹配 foo 字串那行以及上下5行
`grep -B 5 'foo' file` 显示 foo 及前5行
`grep -A 5 'foo' file` 显示 foo 及后5行
`grep -E '123|abc' filename`  找出文件（filename）中包含123或者包含abc的行
`grep pattern1 files | grep pattern2`显示既匹配 pattern1 又匹配 pattern2 的行。

### zgrep
> zgrep 则能够对压缩包内容进行正则匹配。

### ps
> Linux 中的 ps 命令是 Process Status 的缩写。ps 命令用来列出系统中当前运行的那些进程。ps 命令列出的是当前那些进程的快照，就是执行 ps 命令的那个时刻的那些进程，如果想要动态的显示进程信息，就可以使用 top 命令。


**命令格式**：`ps [命令参数]`
**命令参数**：
- `-e`：显示所有进程。
- `f`：显示程序间的关系。
- `-ef`：显示所有进程信息，连同命令行。

**实例**：
`ps -ef | grep 'Bas'`：显示所有进程信息，连同命令行，并且在结果中筛出含有 Bas 关键字的进程。

**结果说明**：
- `UID`：进程被该UID拥有。
- `PID`：进程ID。
- `PPID`：父进程ID。
- `C`：CPU 使用的资源百分比。
- `STIME`：进程启动时间。
- `TTY`：该 process 是在那个终端机上面运作，若与终端机无关，则显示 ?，另外， tty1-tty6 是本机上面的登入者程序，若为 pts/0 等等的，则表示为由网络连接进主机的程序。
- `TIME`：使用掉的 CPU 时间。
- `CMD`：启动命令。

`ps aux`：列出目前所有的正在内存当中的程序。

**结果说明**：
`USER`：该 process 属于那个使用者账号的
`PID` ：该 process 的ID
`%CPU`：该 process 使用掉的 CPU 资源百分比
`%MEM`：该 process 所占用的物理内存百分比
`VSZ` ：该 process 使用掉的虚拟内存量 (Kbytes)
`RSS` ：该 process 占用的固定的内存量 (Kbytes)
`TTY` ：该 process 是在那个终端机上面运作，若与终端机无关，则显示 ?，另外， tty1-tty6 是本机上面的登入者程序，若为 pts/0 等等的，则表示为由网络连接进主机的程序。
`STAT`：该程序目前的状态，主要的状态有
- R ：该程序目前正在运作，或者是可被运作
- S ：该程序目前正在睡眠当中 (可说是 idle 状态)，但可被某些讯号 (signal) 唤醒。
- T ：该程序目前正在侦测或者是停止了
- Z ：该程序应该已经终止，但是其父程序却无法正常的终止他，造成 zombie (疆尸) 程序的状态

`START`：该 process 被触发启动的时间
`TIME` ：该 process 实际使用 CPU 运作的时间
`COMMAND`：该程序的实际指令

### top
> top命令提供了实时的对系统处理器的状态监视.它将显示系统中CPU最“敏感”的任务列表.该命令可以按CPU使用、内存使用和执行时间对任务进行排序。

[https://www.cnblogs.com/peida/archive/2012/12/24/2831353.html](https://www.cnblogs.com/peida/archive/2012/12/24/2831353.html)





### > 指令和 >> 指令
- `>`：重定向，覆盖原来文件的内容。
- `>>`：追加，追加到文件尾部。

1. ls -l > 文件名：列表的内容写入文件中（覆盖）
2. ls -l >> 文件名：列表的内容追加到文件的末尾。

### echo 
> echo命令用于在shell中打印shell变量的值，或者直接输出指定的字符串。

### head
> head命令用于显示文件的开头的内容。

### tail
> tail命令用于输入文件中的尾部内容。tail命令默认在屏幕上显示指定文件的末尾10行。

1. tail -f 文件：实时追踪该文档的所有更新。

### cat more less
- `cat`：连接文件并打印到标准输出设备上，cat经常用来显示文件的内容，当文件较大时，文本在屏幕上迅速闪过（滚屏），用户往往看不清所显示的内容。因此，一般用more等命令分屏显示。
- `more`：它以全屏幕的方式按页显示文本文件的内容，more名单中内置了若干快捷键。
- `less`：less命令的作用与more十分相似，都可以用来浏览文字档案的内容，不同的是less命令允许用户向前或向后浏览文件，而more命令只能向前浏览。

### ln
> 用来为文件创件连接，类似于 windows 系统的快捷方式。

软链也会作为一个文件保存下来，而且需要在软链所在的路径下执行命令才能生效，所以最好使用一个专门的文件存放软链。

- `ln -s /Users/kun/Downloads/电子书/ book`：为指定的目录创建名字为 book 的软链，当我们在软链的目录下执行 `cd book` 的效果与
`cd /Users/kun/Downloads/电子书/` 等同。
- `ln -s /Users/kun/Desktop/shell/bas/bas_login.sh baslogin`：`./baslogin` 与 `.//Users/kun/Desktop/shell/bas/bas_login` 等同。
- `rm -rf ${softlink-name}`：删除软链接。
- `ls -l ${softlink-name}`：查看软链指向地址。

### history
> 使用显示历史指令。

### find locate grep 管道符
1. `find`：用来在指定目录下查找文件。
实例：`find / -name a.txt`，在根目录下查找名字为 a.txt 的文件。（支持正则，如*.txt，则是在根目录下以txt结尾的文件）

2. `locate`：locate 命令其实是 find -name 的另一种写法，但是要比后者快得多，原因在于它不搜索具体目录，而是搜索一个数据库 /var/lib/locatedb，这个数据库中含有本地所有文件信息。Linux系统自动创建这个数据库，并且每天自动更新一次，所以使用 locate 命令查不到最新变动过的文件。为了避免这种情况，可以在使用 locate 之前，先使用 updatedb 命令，手动更新数据库。
3. `grep`：过滤查找。
4. `管道符 |`：表示将前一个命令的处理结果传输给后面的命令处理。

### 压缩和解压类
#### gzip/gunzip 指令
> gzip 用于压缩文件，gunzip用于解压。（gzip 会把原来的文件替换掉）

语法：
- gizp filename：压缩文件，将文件压缩为*.gz 文件。
- gunzip filename.gz ：解压。

#### zip/unzip 指令
语法：
zip -r filename.zip /home：将 home 目录下的所有文件打包到 filename.zip 中。并且保留原来的路径。

#### tar
> 首先要弄清两个概念：打包和压缩。打包是指将一大堆文件或目录变成一个总的文件；压缩则是将一个大的文件通过一些压缩算法变成一个小文件。

> 利用tar命令，可以把一大堆的文件和目录全部打包成一个文件。（最终打包成 .tar.gz 文件）

基本语法：
tar [选项] XXX.tar.gz 打包的内容【可以是多个文件，多个文件用空格隔开】

|选项|功能|
|---|---|
|-c|产生 .tar 打包文件|
|-v|显示详细信息|
|-f|指定压缩后的文件名|
|-z|打包同时压缩|
|-x|解压.tar文件|

#### netstat
`netstat -tunlp`：用于显示 tcp，udp 的端口和进程等相关情况。

netstat 查看端口占用语法格式：
`netstat -tunlp | grep 端口号`
- -t (tcp) 仅显示tcp相关选项
- -u (udp)仅显示udp相关选项
- -n 拒绝显示别名，能显示数字的全部转化为数字
- -l 仅列出在Listen(监听)的服务状态
- -p 显示建立相关链接的程序名

- `netstat -nlp | grep pid`：根据进程 pid 查端口
- - `netstat -nlp | grep port`：根据端口 port 查进程 pid


#### profile、bash_profile 作用与区别

- `profile` 文件的作用
    - profile（/etc/profile），用于 `设置系统级的环境变量和启动程序` ，在这个文件下配置会对 `所有用户` 生效。当用户登录（login）时，文件会被执行，并从/etc/profile.d目录的配置文件中查找shell设置。
    - 添加环境变量后，需要重新登录才能生效，也可以使用source命令强制立即生效。

- `bash_profile` 文件
    - bash_profile只有 `单一用户` 有效，文件存储位于 `~/.bash_profile`，该文件是一个用户级的设置，可以理解为某一个用户的profile目录下。这个文件同样也可以用于配置环境变量和启动程序，但只针对单个用户有效。

- `bashrc` 文件：
    - 这个文件用于 `配置函数或别名`。bashrc文件有两种级别：系统级的位于/etc/bashrc、用户级的~/.bashrc，两者分别会对所有用户和当前用户生效。

- `环境变量`：环境变量是具有特殊名字的一个特定对象，包含了一个或多个应用程序运行所需的信息。（例如PATH，可执行程序的搜索路径，当要求系统运行一个程序，而没告诉系统它的具体路径时，系统就要在PTAH值的路径中寻找此程序，找到去执行）
    - 环境变量不仅有PATH，系统的环境变量还有：
        - C_INCLUDE_PATH 头文件的搜索路径
        - LIBRARY_PATH静态库搜索路径（编译时包含）
        - LD_LIBRARY_PATH动态库搜索路径（链接时包含）
        - ...

- `添加环境变量`：
    1. 在 profile 文件中添加 `export HOST=itbilu.com` ，然后 source profile。
    2. 在任意文件中添加 `export HOST=itbilu.com` ，然后 source fileName。

- `获取环境变量`：${env_name}

- 参考文章：[profile、bash_profile、bashrc文件的作用与区别](https://itbilu.com/linux/management/NyI9cjipl.html)

#### source
> 执行文件并从文件中加载变量及函数到执行环境。

#### alias
> 定义或显示别名。

语法：`alias [-p] [name[=value] ...]`
- `-p`：显示全部已定义的别名。

主要用途：
- 简化较长的命令。
- 定义一个或多个别名。
- 修改一个或多个已定义别名的值。
- 显示一个或多个已定义别名。
- 显示全部已定义的别名。

直接在shell里设定的命令别名，在终端关闭或者系统重新启动后都会失效，如何才能永久有效呢？

使用编辑器打开~/.bashrc，在文件中加入别名设置，如：alias rm='rm -i'，保存后执行source ~/.bashrc，这样就可以永久保存命令的别名了。

- 参考链接：[https://wangchujiang.com/linux-command/c/alias.html](https://wangchujiang.com/linux-command/c/alias.html)

#### linux ~ 目录
- `cd ~`：表示当前用户的根目录 

#### which
which 指令会在环境变量 $path 设置的目录里查找符合条件的文件。

#### whereis
> whereis 命令用于查找文件。

该指令只能用于查找二进制文件、源代码文件和man手册页，一般文件的定位需使用 `locate` 命令。

#### df 和 du
- 我们使用 `df -h` 命令来查看磁盘信息， `-h` 选项为根据大小适当显示：
- `du` 的英文原义为 disk usage，含义为显示磁盘空间的使用情况，用于查看当前目录的总大小。
    - `du -sh`：查看当前目录的大小
    - `du log2012.log `：显示指定文件所占空间

#### su - xxx
- `su - clsung`：变更帐号为 clsung 并改变工作目录至 clsung 的家目录（home dir）

home 目录：每个用户自己的地盘。

#### sudo
Linux sudo 命令以系统管理者的身份执行指令，也就是说，经由 sudo 所执行的指令就好像是 root 亲自执行。

#### wget
Linux系统中的wget是一个下载文件的工具，它用在命令行下。wget支持HTTP，HTTPS和FTP协议。

#### Linux 目录规范
![image.png](https://i.loli.net/2020/06/07/dNVOfgxU9W6w2AS.png)

- `/bin`：放置的是单人维护模式下还能够操作的指令，主要有：cat、chmod、cp等等常用指令。
- `/boot`：放置开机会使用到的文件。
- `/etc`：系统主要的配置文件几乎都放在这个目录内。
- `/home`：这是系统默认的用户家目录。在你心中一个一般使用者账号时，默认的用户家目录都会规范到这里。
- `/root`：root 用户的家目录（牛逼）。
- `/tmp`：这是让一般使用者或者正在执行的程序暂时放置文件的地方，这个目录是任何人都能够存取的，所以你需要定期的清理一下。当然，重要数据不可放置在此目录，因为 FHS 甚至建议在开机时，应该要将 /tmp 下的数据都删除！

![image.png](https://i.loli.net/2020/06/07/AyTLVwJiF8sW3ea.png)

usr 是 Unix Software Resource 的缩写。FHS 建议所有软件开发者，应该将他们的数据合理的分别放置到这个目录下的次目录，而不要自行创建该软件自己独立的目录。

一般来说，/usr 的次目录建议有底下这些：
![image.png](https://i.loli.net/2020/06/07/lystjJznS3uUDrA.png)

![image.png](https://i.loli.net/2020/06/07/aGdYzlk4tU9ZPgA.png)


- [https://blog.csdn.net/l740450789/article/details/49099371](https://blog.csdn.net/l740450789/article/details/49099371)

### wc
> Linux系统中的wc(Word Count)命令的功能为统计指定文件中的字节数、字数、行数，并将统计结果显示输出。

- 语法：`wc [参数] 文件`

参数：
- `-c` 统计字节数。
- `-l` 统计行数。
- `-m` 统计字符数。这个标志不能与 -c 标志一起使用。
- `-w` 统计字数。一个字被定义为由空白、跳格或换行字符分隔的字符串。
- `-L` 打印最长行的长度。
- `-help` 显示帮助信息
- `--version` 显示版本信息


