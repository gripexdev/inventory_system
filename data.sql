USE inventory_management;

-- Insertion des données dans `logs`
INSERT INTO `logs` (`id`, `user`, `action`, `createdAt`) VALUES
(654, 'omar', 'Authentification', '2024-12-27 17:05:31'),
(655, 'omar', 'Affichage de la liste des produits', '2024-12-27 17:05:31'),
(656, 'omar', 'Produit Supprimé: Clavier', '2024-12-27 17:05:40'),
(657, 'omar', 'Affichage de la liste des produits', '2024-12-27 17:05:40'),
(658, 'omar', 'Utilisateur déconnecté', '2024-12-27 17:05:43'),
(659, 'othmane', 'Authentification', '2024-12-27 17:06:01'),
(660, 'othmane', 'Affichage de la liste des produits', '2024-12-27 17:06:01'),
(661, 'othmane', 'Produit ajouté : Calculatrice, Catégorie : électronique', '2024-12-27 17:06:36'),
(662, 'othmane', 'Utilisateur déconnecté', '2024-12-27 17:06:40'),
(663, 'omar', 'Authentification', '2024-12-27 17:07:07'),
(664, 'omar', 'Affichage de la liste des produits', '2024-12-27 17:07:07'),
(665, 'omar', 'Produit mis à jour avec succès.\nAnciennes données - Nom: Sony PlayStation 5, Catégorie: Gaming Console, Prix: 499,99, Quantité: 25\nNouvelles données - Nom: Sony PlayStation 5, Catégorie: Gaming Console, Prix: 499,99, Quantité: 35', '2024-12-27 17:07:17'),
(666, 'omar', 'Affichage de la liste des produits', '2024-12-27 17:07:18'),
(667, 'omar', 'Affichage de la liste des logs', '2024-12-27 17:07:19'),
(668, 'omar', 'Affichage de la liste des produits', '2024-12-27 17:07:25'),
(669, 'omar', 'Utilisateur déconnecté', '2024-12-27 17:07:27'),
(670, 'othmane', 'Authentification', '2024-12-27 17:07:41'),
(671, 'othmane', 'Affichage de la liste des produits', '2024-12-27 17:07:41'),
(672, 'othmane', 'Produit Supprimé: Calculatrice', '2024-12-27 17:07:58'),
(673, 'othmane', 'Affichage de la liste des produits', '2024-12-27 17:07:58'),
(674, 'othmane', 'Affichage de la liste des logs', '2024-12-27 17:08:02'),
(675, 'othmane', 'Affichage de la liste des produits', '2024-12-27 17:08:14'),
(676, 'othmane', 'Affichage de la liste des logs', '2024-12-27 17:08:15'),
(677, 'othmane', 'Utilisateur déconnecté', '2024-12-27 18:04:10');

-- Insertion des données dans `products`
INSERT INTO `products` (`id`, `name`, `category`, `quantity`, `price`) VALUES
(1, 'Apple iPhone 14', 'Smartphone', 10, 799.99),
(2, 'Samsung Galaxy S22', 'Smartphone', 5, 699.99),
(3, 'Dell XPS 13', 'Laptop', 20, 999.99),
(4, 'Sony WH-1000XM5', 'Headphones', 15, 349.99),
(5, 'Nike Air Max 270', 'Footwear', 30, 149.99),
(6, 'MacBook Pro 16\"', 'Laptop', 6, 2399.99),
(7, 'Fitbit Charge 5', 'Smartwatch', 50, 149.99),
(8, 'Canon EOS R5', 'Camera', 8, 3899.99),
(9, 'Nintendo Switch', 'Gaming Console', 12, 299.99),
(10, 'Samsung QLED 65\" TV', 'Television', 10, 1499.99),
(11, 'Sony PlayStation 5', 'Gaming Console', 35, 499.99);

-- Insertion des données dans `users`
INSERT INTO `users` (`id`, `name`, `email`, `password`) VALUES
(1, 'othmane', 'othmane@othmane.com', 'othmaneothmane'),
(47, 'omar', 'omar@omar.com', 'omaromar');
