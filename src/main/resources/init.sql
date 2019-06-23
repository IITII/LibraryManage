create database library;
go
use library;
go
--学生信息表
create table student(sid int primary key ,
                     sna varchar(20) not null ,
                     sde varchar(20) not null ,
                     ssp varchar(20) not null );
go
--图书信息
create table book(bno varchar(32) primary key ,
                  bna varchar(64) not null unique ,
                  bda date not null ,
                  bpu varchar(32),
                  bpl varchar(1024) not null ,
                  bpr smallmoney not null ,
                  MAX_NUMBER smallint not null check (MAX_NUMBER >=0),
                  currentNumber smallint not null check (currentNumber >= 0));
go
--在图书的出版社列上建立非聚合索引
create nonclustered index book_bpu on
    book(bpu);
--在图书名称上建立非聚合索引
create nonclustered index book_bna
    on book(bna);
go
--借书证
create table card(sno int primary key ,
                  sid int unique foreign key references student(sid),
                  MAX_ORDER smallint not null check (MAX_ORDER > 0),
                  ordered smallint default 0 check (ordered >= 0),
                  bill money
)
go
--借阅记录表
create table record(recordID int identity(1,1) primary key ,
                    sno int not null foreign key references card(sno),
                    bno varchar(32) not null foreign key references book(bno),
                    status char(8) not null default '未还' check (status='未还'or status='已还'or status='超期'or status='丢失'),
                    startTime datetime ,
                    endTime datetime)
go

--罚款表
create table fine(recordID int primary key foreign key references record(recordID),
                  fineType char(4) not null check (fineType='超期'or fineType='丢失'),
                  fine smallmoney not null );
go

--借阅触发器
/* create trigger borrow_book on record after insert as
    begin
        declare @bookNumber smallint
        select @bookNumber=currentNumber from book
        if @bookNumber > 0
        update book set currentNumber = @bookNumber -1
        where  bno in (
            select bno from inserted
            )
        else
        raiserror ('当前欲借书籍数量已不足，无法借阅',16,1)
        rollback transaction
    end
    GO */
--还书触发器
/*create trigger return_book on record after update as
    begin
        declare @status int, @money smallmoney,@fine smallmoney,@bookNumber smallint,@startTime date,@endTime date,@total smallint,@bno varchar(32),@sno int,@recordID int
            select @bookNumber=currentNumber, @total = MAX_NUMBER from book
            select @startTime = startTime from record
            select @endTime = endTime,@bno = bno, @sno = sno,@recordID=recordID from inserted
            select @money = bill from card
        if update(status)
        begin
            if @status = '已还'
            --更新书籍的当前数量（库存+1）
            update book set currentNumber = @bookNumber + 1 where bno = @bno;

            if @status = '超期'
            select @fine=DATEDIFF(DAY,@startTime,@endTime) * 0.2;
            --更新卡余额
            update card set bill=@money-@fine where  card.sno = @sno;
            --插入罚款记录
            insert into fine values (@recordID,'超期',@fine);
            --更新书籍的当前数量（库存+1）
            update book set currentNumber = @bookNumber + 1 where book.bno = @bno;

            if @status = '丢失'
            select @fine = bpr * 5 from book where book.bno = @bno;
            --更新卡余额
            update card set bill = @money where card.sno = @sno;
            --插入罚款记录
            insert into fine values (@recordID,'丢失',@fine);
            --更新书籍的最大数量（库存-1）
            update book set MAX_NUMBER = @total - 1 where book.bno = @bno;

        end
    end
    GO*/
--查看详细借阅信息的存储过程
create procedure get_record_info_by_recordID @recordID int as
    if @recordID is not null
    select * from record where recordID = @recordID;
    else
        select * from record;
        GO
--查看图书信息存储过程
create procedure get_book_info_by_bpu_or_bno_or_bna @bno varchar(32),@bna varchar(64),@bpu varchar(32) as
    --当书籍编号非空时
    if @bno is not null
        select * from book
            where bno = @bno;
        --当书籍名称非空，出版社为空
        else if @bna is not null and @bpu is null
            select * from book where bna like @bna;
        --当书籍名称为空，出版社非空
        else if @bna is null and @bpu is not null
            select * from book where @bpu like @bpu;
            --当书籍名称非空，出版社非空
            else select * from book where @bpu like @bpu and bna like @bna;
            go
create procedure getBookInfo @bno varchar(32) as
    if @bno is null
        select * from book;
        else
            select * from book where bno like @bno;
            GO
--借书存储过程
create procedure order_book @sno int,@bno varchar(32) as
begin
    declare @currentNumber smallint,@totalTime int, @MAX_ORDER smallint,@ordered smallint,@startTime datetime
    select @currentNumber=currentNumber from book where bno=@bno;
    --??
    select @startTime = startTime from record where sno = @sno;
    select @MAX_ORDER=MAX_ORDER,@ordered=ordered from card where sno = @sno;
    select @totalTime=DATEDIFF(DAY,@startTime,GETDATE());
    --有超期的书
    if @totalTime > 60
        return -1;
    --达到借书上限
    if @ordered >= @MAX_ORDER
        return -2
    --检查书籍库存情况
    if @currentNumber <= 0
        return -3;

    --添加借阅记录，更新书籍库存和借书证的借书数量
     insert into record(sno, bno, status, startTime, endTime) values (@sno,@bno,'未还',getdate(),NULL);
     update book set currentNumber = @currentNumber -1 where bno = @bno;
     update card set ordered = @ordered + 1 where sno = @sno;

end
return 0;
GO
--CADD
drop procedure return_book;
go

create procedure return_book @recordID int as
begin
        declare @sno int,@bno varchar(32),@status char(8),@diffTime int,@fine float,@currentNumber smallint,@ordered smallint,@endTime datetime
        select @diffTime = DATEDIFF(DAY,startTime,GETDATE()),@endTime=GETDATE(), @sno = sno,@bno=bno,@status=status from record where recordID = @recordID;
        select @currentNumber = currentNumber from book where bno = @bno;
        select @ordered = ordered from card where sno = @sno;
        update book set currentNumber = @currentNumber + 1 where bno = @bno;
        update card set ordered = @ordered - 1 where sno = @sno;
        if not exists(select * from record where recordID = @recordID )
            return -1;
        if  @diffTime > 60
            begin
         update record set status = '超期' where recordID = @recordID;
         select @fine = (@diffTime-60)*0.02
         insert into fine values (@recordID,'超期',@fine);
         return 1;
        end
            else
        update record set status = '已还',endTime = @endTime where recordID = @recordID;
        return 0;
end
go

--还书存储过程
/*create procedure pro_return_book @record int, @status char(8) as
    begin
    if @status = '已还'
    update record set status = @status, endTime = getdate() where recordID = @record;
    else if @status = '超期' or @status = '丢失'
        update record set status = @status where recordID = @record;
    end
GO*/
--date格式：月/日/年
insert into book values
('1', '组合数学', '11/05/2012','机械工业出版社', 'B-203', 35,  5, 5),
('2', '算法竞赛入门经典训练指南', '5/11/2009','清华大学出版社','B-205' , 45, 8,8),
('3', '算法竞赛入门经典（第二版）', '06/13/2012','清华大学出版社','B-203',80,7,7),
('4', '挑战程序设计竞赛2','12/05/2012', '人民邮电出版社', 'B-203', 12,4,4),
('5', '算法设计与分析',  '12/08/2012','清华大学出版社','B-203', 50,10,10);
go
insert into student values (1,'小灰灰','软件学院','嵌入式系统开发'),
                           (2,'大灰灰','信工学院','物联网');
go
insert into card values (1,1,5,0,500),
                        (2,2,4,0,0);
go
/*
use library
drop table fine,record,card,book,student;
use master;
drop database library;
go

create login IITII with password = 'Nchu172041';
create user IITII for login IITII;
go
grant select ,update ,execute ,delete to IITII;
go
*/
