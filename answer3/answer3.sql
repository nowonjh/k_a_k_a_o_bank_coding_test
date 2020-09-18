SELECT usr_no												AS '사용자번호',
	IF(gender_code IS NULL,	'-', 
		IF(gender_code % 2 = 1, '남', '여'))	AS '성별',
	IF(birth_day IS NULL,	'-', 
		Floor(((200626 - birth_day)
		+ (IF(gender_code > 2, 0, 1) * 1000000)) / 10000)) 	AS '나이',
	IF(loc_nm IS NULL,		'-', loc_nm)					AS '지역명',
	IF(last_loc_nm IS NULL,	'-', last_loc_nm)				AS '이전지역명',
	IF(mcco_nm IS NULL,		'-', mcco_nm)					AS '이동통신사명',
	IF(min_log_tktm IS NULL,'-', min_log_tktm)				AS '가입일',
	IF(mode_menu IS NULL,	'-', mode_menu)					AS '최빈메뉴',
	IF(recent_menu IS NULL,	'-', recent_menu)				AS '최근메뉴'
FROM (
		SELECT	usr_no,
			SUBSTRING(min_log_tktm, 1, 8) AS min_log_tktm,
			rsdt_no,
			loc_nm,
			last_loc_nm,
			mcco_nm,
			mode_menu,
			recent_menu,
			SUBSTRING(rsdt_no, 7, 1) AS gender_code,
			SUBSTRING(rsdt_no, 1, 6) AS birth_day
		FROM (
				SELECT	usr_no,
					MIN(log_tktm) min_log_tktm,
					(
						SELECT rsdt_no
						FROM	USR_INFO_CHG_LOG u
						WHERE  u.log_id = MAX(IF(usr.rsdt_no = '', NULL, usr.log_id))) AS rsdt_no,
					(
						SELECT loc_nm
						FROM	USR_INFO_CHG_LOG u
						WHERE  u.log_id = MAX(IF(usr.loc_nm = '', NULL, usr.log_id))) AS loc_nm,
					(
						SELECT mcco_nm
						FROM	USR_INFO_CHG_LOG u
						WHERE  u.log_id = MAX(IF(usr.mcco_nm = '', NULL, usr.log_id))) AS mcco_nm,
					(
						SELECT	LAG(loc_nm) OVER(ORDER BY usr_no, log_tktm)
						FROM	USR_INFO_CHG_LOG u
						WHERE	u.loc_nm <> ''
									AND	u.usr_no = usr.usr_no
						ORDER BY usr_no,
								log_tktm DESC
						LIMIT	1) AS last_loc_nm,
					(
						SELECT	MAX(menu_nm) OVER(PARTITION BY usr_no ORDER BY COUNT(*) DESC) cnt
						FROM	menu_log m
						WHERE	m.usr_no = usr.usr_no
						AND	m.menu_nm NOT IN ('login',
												'logout')
						GROUP BY menu_nm
						ORDER BY COUNT(*) DESC
						LIMIT	1) AS mode_menu,
					(
						SELECT	menu_nm
						FROM	menu_log m
						WHERE	m.usr_no = usr.usr_no
						AND	m.menu_nm NOT IN ('login',
												'logout')
						ORDER BY m.log_tktm DESC
						LIMIT	1) AS recent_menu
				FROM	USR_INFO_CHG_LOG AS usr
				GROUP BY usr_no) sub
		ORDER BY usr_no) main;