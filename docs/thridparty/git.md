> 什么分支应该推送到远程仓库？取决于你是否需要和你的小伙伴合作在该分支上面开发。如果不需要，你就自己藏着玩，合并到主分支之后，默默删除就好。

## 常用指令
- `git log`：显示提交日志。
- `git log --pretty=oneline`：一行显示提交日志。
- `git clone -o remote-name git@server-name:path/repo-name.git`：使用 -o 执行远程仓库名。
- `git branch -a`：查看所有分支（包括远程）
- `git branch -d branch-name`：删除本地分支
- `git push origin --delete branch-name`：删除远程分支
- `git log --graph`：查看分支合并图。
- - `git log --graph --pretty=oneline --abbrev-commit`：优雅的查看分支合并图。
- `git remote`：查看远程库信息。
- `git remote -v`：显示更详细的远程库信息。



## Git 中 origin 的含义
参考文章：
- [Git 中 origin 的含义](https://www.cnblogs.com/xuyaowen/p/git-origin.html)

## Git 回退
- `git reset --hard HEAD^`：回退到上一个版本，HEAD 表示当前版本，HEAD^ 表示上一个版本。这里的回退只是本地回退，不影响远程仓库。回退之后，响应的 git log 也会隐藏。（git reset 可以向后，也可以向前进行，只要我们知道版本的 commitid 即可）

Git的版本回退速度非常快，因为Git在内部有个指向当前版本的HEAD指针，当你回退版本的时候，Git仅仅是把HEAD从指向你想要回退的位置：

![WechatIMG527.jpeg](https://i.loli.net/2019/08/31/WgTecy38DZMi54u.jpg)

## 工作区与暂存区
1. `git add`：把文件添加进去，实际上就是把文件修改添加到`暂存区`。
2. `git commit`：提交更改，实际上就是把暂存区的所有内容提交到当前分支。

![WechatIMG528.jpeg](https://i.loli.net/2019/08/31/aEI7P2ndu6bSLQW.jpg)

## 撤销修改
> 在这里你可以放弃本地对某个文件的修改。

- 修改还没提交到暂存区
`git checkout -- filename`：把 filename文件在工作区的修改全部撤销，撤销修改就回到和版本库一模一样的状态。（这里的 -- 很重要，要不然就变成切换分支了）

- 修改已经提交到暂存区
    1. `git reset HEAD <file>`：可以把暂存区的修改撤销掉（unstage），重新放回工作区。
    2. 现在已经把修改内容从暂存区拿回来到工作区了，所以还记得如何丢弃工作区的修改吗？就是执行`git checkout -- filename`。

小结：
- 场景1：当你改乱了`工作区`某个文件的内容，想直接丢弃工作区的修改时，用命令`git checkout -- file`。

- 场景2：当你不但改乱了工作区某个文件的内容，还添加到了`暂存区`时，想丢弃修改，分两步，第一步用命令`git reset HEAD <file>`，就回到了场景1，第二步按场景1操作。

- 场景3：已经提交了不合适的修改到`版本库时`，想要撤销本次提交，参考版本`回退`一节，不过前提是没有推送到远程库。

## 添加远程仓库
- 要关联一个远程库，使用命令 `git remote add origin git@server-name:path/repo-name.git`：添加后，远程库的名字就是origin，这是Git默认的叫法，也可以改成别的。

- 关联后，使用命令 `git push -u origin master` 第一次推送master分支的所有内容。

## stash
> `stash` 可以把当前工作现场“储藏”起来，等以后恢复现场后继续工作。

假设我们正在 dev 分支上开发，这时候突然要切换一个新的分支去修复一个新的bug，但是这时候 dev 分支上的功能还没有开发完毕，这时候我们也不想 commit 开发到一半的功能。我们就可以使用 stash 把当前 dev 分支的内容存储起来，stash 之后，dev 分支就是干净的了。

- 使用 `git stash list` 查看 stash 的情况。
- 恢复现场：
    - 用 `git stash apply`恢复，但是恢复后，stash内容并不删除，你需要用 `git stash drop`来删除。
    - 用 `git stash pop`，恢复的同时把 stash 内容也删了。


## 标签的使用
> 默认标签是打在最新提交的 commit 上的。

在Git中打标签首先要切换到需要打标签的分支上

- 命令 `git tag <tagname>` 用于新建一个标签，默认为 `HEAD`，也可以指定一个commit id。

- 命令 `git tag -a <tagname> -m "blablabla..."`可以指定标签信息。

- 命令 `git tag` 可以查看所有标签。
- `git tag -d v0.1`：删除标签。
- `git push origin <tagname>`：推送某个标签到远程。
- `git push origin --tags`：一次性推送全部尚未推送到远程的本地标签。
- `git push origin :refs/tags/<tagname>`：删除远程标签。

## git rebase
> `git merge` 和 `git rebase` 都可以用来合并分支，但是如果你想让分支历史看起来像没有经过任何合并一样，也可以用 `git rebase`。

在 rebase 的过程中，也许会出现冲突。在这种情况，Git 会停止 rebase 并会让你去解决冲突；在解决完冲突后，用 git add 命令去更新这些内容的暂存区，然后，你无须执行 git commit ，只要执行 `git rebase --continue`，这样 git 会继续应用（apply）剩下的补丁。

## 配置别名
`git config --global alias.st status`：执行之后，st 就表示 status。

参考文章：
- [廖雪峰老师Git教程](https://www.liaoxuefeng.com/wiki/896043488029600)