INSERT INTO users (name, email, username, password)
SELECT 'Doe J', 'doe@example.com', 'doejohn', 'securePassword12345'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE username = 'doejohn'
    OR email = 'doe@example.com'
    OR name = 'Doe J'
);