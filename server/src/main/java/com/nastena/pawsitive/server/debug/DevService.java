package com.nastena.pawsitive.server.debug;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@ConditionalOnProperty(value = "custom.dev-mode", havingValue = "true")
public class DevService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void clearAllTables() {
        log.info("Clearing all tables...");

        // Disable triggers temporarily
        jdbcTemplate.execute("SET session_replication_role = 'replica'");

        try {
            // Get all table names
            List<String> tableNames = jdbcTemplate.queryForList(
                    "SELECT tablename FROM pg_catalog.pg_tables " +
                            "WHERE schemaname = 'public'", String.class);

            if (!tableNames.isEmpty()) {
                // Build TRUNCATE statement with CASCADE
                String truncateSql = "TRUNCATE TABLE " +
                        String.join(", ", tableNames) +
                        " CASCADE";

                jdbcTemplate.execute(truncateSql);
                log.info("Cleared {} tables", tableNames.size());
            }

        } finally {
            // Re-enable triggers
            jdbcTemplate.execute("SET session_replication_role = 'origin'");
        }
    }

}
