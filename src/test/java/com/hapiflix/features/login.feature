# Language: ro
# Creează fișierul login.feature:
# Acest fișier text descrie funcționalitatea de login într-un limbaj natural.

Feature: Autentificarea utilizatorului pe platforma Hapiflix
  Scenario: Autentificare reușită cu credențiale valide
    Given Utilizatorul se află pe pagina de login
    When El introduce username-ul "tautester" și parola "qazXSW"
    And Apasă pe butonul de login
    Then El este redirecționat către pagina principală a aplicației