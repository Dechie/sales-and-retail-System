# Zara Store

This project has been migrated from a JSP-based codebase to Laravel. The new tech stack includes:

- **Backend**: Laravel (PHP)
- **Frontend**: Blade templates with tailwindcss
- **Database**: MySQL/Mariadb

## Changes:

- Replaced JSP code with Laravel.
- Updated routes, controllers, and models according to Laravel's MVC structure.
- Rewrote the bootstrap styling code with tailwind

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Dechie/sales-and-retail-System.git
   cd sales-and-retail-System 
   ```
2. Install dependencies:
   ```bash
   composer install
   ```
3. Set up the environment:
   ```bash
   cp .env.example .env
   php artisan key:generate
   php artisan migrate
   ```
4. serve the application
   ```bash
   php artisan serve
   ```

## Contributing

We welcome contributions! Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add new feature'`).
5. Push to your branch (`git push origin feature-branch`).
6. Create a pull request.
