package com.kakao.codingtest.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kakao.codingtest.taskinfo.vo.SourceVO;
import com.kakao.codingtest.worker.vo.RequestJDBCQueryVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yuganji
 *
 */
@Slf4j
public class JdbcManager {
    private Connection getConnection(SourceVO source) throws ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            Class.forName(source.getDriverClassName());
            con = DriverManager.getConnection(source.getUrl(), source.getUsername(), source.getPassword());
            return con;
        } catch (SQLException e) {
            throw new SQLException("SQL Error: " + e.getMessage() + " - " + source.toString());
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Can not found JDBC driver: " + source.getDriverClassName());
        }
    }

    private void close(Object obj) {
        if (obj != null) {
            try {
                if (obj instanceof Connection) {
                    ((Connection) obj).close();
                } else if (obj instanceof ResultSet) {
                    ((ResultSet) obj).close();
                } else if (obj instanceof PreparedStatement) {
                    ((PreparedStatement) obj).close();
                }
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public List<Map<String, Object>> query(SourceVO sourceVO, RequestJDBCQueryVO queryVO) throws ClassNotFoundException, SQLException {
        Connection con = this.getConnection(sourceVO);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(queryVO.getTableName());
        sb.append(" WHERE 1 = 1");
        if (sourceVO.getAdditionalQuery().length() > 0) {
            sb.append(" AND ");
            sb.append(sourceVO.getAdditionalQuery());
        }
        sb.append(" AND ");
        sb.append(queryVO.getTimeField());
        sb.append(" BETWEEN ? AND ?");
        PreparedStatement ps = con.prepareStatement(sb.toString());
        ps.setFetchSize(2000);
        ps.setString(1, queryVO.getStartTime());
        ps.setString(2, queryVO.getEndTime());
        log.debug("query: " + sb.toString());
        ResultSet rs = ps.executeQuery();

        List<Map<String, Object>> result = new ArrayList<>();
        List<String> columns = new ArrayList<>();
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            columns.add(rs.getMetaData().getColumnName(i + 1).toLowerCase());
        }
        log.debug("Found colums:" + columns);
        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            for (String col: columns) {
                row.put(col, rs.getObject(col));
            }
            result.add(row);
        }
        this.close(rs);
        this.close(ps);
        this.close(con);
        return result;
    }
}
