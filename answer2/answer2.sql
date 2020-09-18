SELECT
	t1.menu_nm										AS '메뉴명',
	t1.lag_menu_nm									AS '이전 메뉴명',
	COUNT(*)										AS '접근 건수',
	TRUNCATE(COUNT(*) / SUM(COUNT(*))
		OVER (PARTITION BY t1.menu_nm) * 100, 2) 	AS '비율(%)'
FROM (
	SELECT
		log_id,
		menu_nm,
		LAG(menu_nm)
		OVER(ORDER BY usr_no, log_id) AS lag_menu_nm
	FROM
		MENU_LOG
	WHERE
		menu_nm NOT IN ( 'login', 'logout' )
) t1
WHERE
	t1.lag_menu_nm IS NOT NULL
GROUP BY
	t1.menu_nm, t1.lag_menu_nm
ORDER BY
	t1.menu_nm,
	COUNT(*) DESC,
	lag_menu_nm;