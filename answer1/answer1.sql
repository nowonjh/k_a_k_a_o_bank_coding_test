SELECT
    CONCAT('Top', ranking, ' 메뉴')                                       AS '구분',
    MAX(IF(main.wd = 0, CONCAT(main.menu_nm, ' (', main.cnt, ')'), '-')) AS '월',
    MAX(IF(main.wd = 1, CONCAT(main.menu_nm, ' (', main.cnt, ')'), '-')) AS '화',
    MAX(IF(main.wd = 2, CONCAT(main.menu_nm, ' (', main.cnt, ')'), '-')) AS '수',
    MAX(IF(main.wd = 3, CONCAT(main.menu_nm, ' (', main.cnt, ')'), '-')) AS '목',
    MAX(IF(main.wd = 4, CONCAT(main.menu_nm, ' (', main.cnt, ')'), '-')) AS '금',
    MAX(IF(main.wd = 5, CONCAT(main.menu_nm, ' (', main.cnt, ')'), '-')) AS '토',
    MAX(IF(main.wd = 6, CONCAT(main.menu_nm, ' (', main.cnt, ')'), '-')) AS '일'
FROM (
    SELECT
        ROW_NUMBER()
             OVER (
                PARTITION BY t1.wd
                ORDER BY t1.cnt DESC, t1.menu_nm) AS ranking,
        t1.wd,
        t1.menu_nm,
        CAST(t1.cnt AS CHAR) AS cnt
    FROM (
        SELECT
            WEEKDAY(log_tktm) AS wd,
            menu_nm,
            COUNT(*) AS cnt
        FROM
            MENU_LOG
        WHERE
            menu_nm NOT IN ( 'logout', 'login' )
        GROUP BY
            wd,
            menu_nm
    ) t1
) main
WHERE
    main.ranking <= 10
GROUP BY
    main.ranking;