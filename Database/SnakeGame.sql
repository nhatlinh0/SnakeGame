create database SnakeGame
go
use SnakeGame
go

create table PlayerAccounts
(PlayerID int primary key,
Username varchar(30) unique,
Password varchar(20),
)

create table GameMode 
(ModeID int primary key,
PlayerID int references PlayerAccounts(PlayerID))

create table Score 
(PlayerID int references PlayerAccounts(PlayerID),
Score INT CHECK (Score >= 0)
)

create table Reward
(RewardID int primary key,
Code varchar(30))

insert into PlayerAccounts values(1,'linh','123')
insert into PlayerAccounts values(2,'quang','123')
select * from PlayerAccounts

select * from PlayerAccounts where PlayerAccounts.Username = 'linh' and PlayerAccounts.Password = '123'

insert into Score values(1,100)
insert into Score values(2,200)
select * from Score

insert into Reward values(1,'LIFE15K')
insert into Reward values(2,'ELMG2TR')
insert into Reward values(3,'FMCGTTM21')
insert into Reward values(4,'LIFES100K')
insert into Reward values(5,'LIVE-765609722003456')
insert into Reward values(6,'LIVE-765609730129920')
insert into Reward values(7,'FA2450K')
insert into Reward values(8,'ELMG5TR')
insert into Reward values(9,'FMCGTTM22')
insert into Reward values(10,'ELMG500')
insert into Reward values(11,'FA2440K')
insert into Reward values(12,'SGRO2411')
insert into Reward values(13,'SBD3CETK2')
insert into Reward values(14,'ELMG1TR')
insert into Reward values(15,'LIFEABS150')
insert into Reward values(16,'ICBFRI40')
insert into Reward values(17,'LIFEBIKE100')
insert into Reward values(18,'LIFEBIKE250')
insert into Reward values(19,'AFFINCUM')
insert into Reward values(20,'AFFNSALE')

select * from Reward 
delete Reward where Reward.RewardID = 1

update Score set Score = 100 where Score.PlayerID=1
select Score from Score,PlayerAccounts where Score.PlayerID = PlayerAccounts.PlayerID and PlayerAccounts.Username = 'linh'


UPDATE Score SET Score = 300 FROM Score, PlayerAccounts WHERE Score.PlayerID = PlayerAccounts.PlayerID and PlayerAccounts.Username = 'linh'
select Score from Score where Score.PlayerID ='1'
