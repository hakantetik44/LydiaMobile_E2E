@wigl @accueil
Feature: Navigation vers la page d'accueil de l'application Wigl
  En tant qu'utilisateur de Wigl
  Je veux accéder à la page d'accueil
  Afin de pouvoir utiliser l'application

  @smoke @homepage @language
  Scenario: Accès à la page d'accueil et sélection de la langue française
    Given l'application Wigl est lancée
    Then l'utilisateur devrait voir les éléments de la page d'accueil
    When l'utilisateur voit les options de langue
    And l'utilisateur sélectionne "Français"
    Then l'application devrait être en français

  @login
  Scenario: Connexion avec des identifiants valides
    Given l'application Wigl est lancée
    When l'utilisateur saisit l'email "utilisateur@wigl.fr" et le mot de passe "MotDePasse123"
    And l'utilisateur appuie sur le bouton de connexion
    Then l'utilisateur devrait voir la page d'accueil
    And l'utilisateur devrait voir son montant de cashback
    And l'utilisateur devrait voir son solde crypto

 