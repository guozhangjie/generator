USE [master]
GO
/****** 对象:  StoredProcedure [dbo].[StopLogin]    脚本日期: 10/20/2016 16:04:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


 

CREATE PROCEDURE [dbo].[StopLogin]

    @Dname varchar(50) 

AS

DECLARE

    @name varchar(50), 

    @s varchar(1000)

BEGIN

    IF (@Dname = '')

    BEGIN

       DECLARE DataName CURSOR FOR

       SELECT name FROM sysdatabases WHERE name not in ('master')

 

       OPEN DataName

 

       FETCH NEXT FROM DataName

       INTO @name

 

       WHILE (@@FETCH_STATUS = 0)

       BEGIN  

           DECLARE tb CURSOR local    

           FOR  

           SELECT N'kill   '+CAST(spid AS varchar)  

           FROM master..sysprocesses    

           WHERE dbid=db_id(@name)  

 

           OPEN tb    

 

           FETCH next FROM tb INTO @s  

 

           WHILE @@FETCH_STATUS=0  

           BEGIN  

               EXEC(@s)  

 

               FETCH NEXT FROM tb INTO @s  

           END  

 

           CLOSE tb  

           DEALLOCATE tb

                        

           FETCH NEXT FROM DataName

           INTO @name

       END

 

       CLOSE DataName

       DEALLOCATE DataName

    END

    ELSE

    BEGIN

       DECLARE tb CURSOR local    

       FOR  

       SELECT N'kill   '+CAST(spid AS varchar)  

       FROM master..sysprocesses    

       WHERE dbid=db_id(@Dname)  

 

       OPEN tb   

 

       FETCH next FROM tb INTO @s 

 

       WHILE @@FETCH_STATUS=0  

       BEGIN  

           EXEC(@s)  

 

           FETCH NEXT FROM tb INTO @s  

       END  

 

       CLOSE tb  

       DEALLOCATE tb

    END

END
