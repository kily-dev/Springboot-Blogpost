# Springboot Blog Project

this project was made for a JEE school project using Springboot. this project requires Node.js, and MySQL, Java 21, and Tailwind.css.


## Configurations

there are certain configurations required to get the project up and running in your own device:

## 1. Configuring Database Settings
the database connection is configured in the `src/main/resources/application.properties` file.

change the username and password to what you have setup in your MySQL database:
```
spring.datasource.username=[replace with your MySQL username]
spring.datasource.password=[replace with your MySQL password]
```

and you also have to create a database named `blog`, as it is required by this property: `spring.datasource.url=jdbc:mysql://localhost:3306/blog`.
you can change it to your liking though.

## 2. Tailwind building
be sure to have Node.js for this part.

in a terminal, browse to the directory of the project (if you're using an IDE, chances are the terminal is already in that location).

execute the following commands:

```
npm init -y
npm i -D tailwindcss
npx tailwindcss init
```

after this, go to the generated `tailwind.config.js` file, modify the content array to this:
```
content: [
    "./src/main/resources/templates/**/*.html"
  ]
```

after that, all you need to do is build Tailwind:
```
npx tailwindcss build -i src/main/resources/static/input.css -o src/main/resources/static/output.css
```

now everything should work just fine. you will have to rebuild Tailwind if you use something that hasn't been built yet.


# Showcase

<img src="https://github.com/kily-dev/Springboot-Blogpost/assets/131630508/0d0d1b9f-4d92-480c-ae31-3e45d7826aa0" height="200">
<img src="https://github.com/kily-dev/Springboot-Blogpost/assets/131630508/efb5d73e-3a3a-4680-9240-69fcb289fb32" height="200">
<img src="https://github.com/kily-dev/Springboot-Blogpost/assets/131630508/d91fedca-38ce-4bde-a38a-3690dc29f2eb" height="200">
<img src="https://github.com/kily-dev/Springboot-Blogpost/assets/131630508/1fe8d9c1-b268-486a-a0bf-4e9084c7f45d" height="200">
<img src="https://github.com/kily-dev/Springboot-Blogpost/assets/131630508/0d3a141d-ad95-4079-9cff-a3bbd49e7887" height="200">
<img src="https://github.com/kily-dev/Springboot-Blogpost/assets/131630508/09351396-2a9a-4548-9865-8cdf3e6695ff" height="200">
<img src="https://github.com/kily-dev/Springboot-Blogpost/assets/131630508/d95eaa94-4fc6-4535-affb-2c31a3f0b84f" height="200">

Credits to https://tailwind-nextjs-starter-blog.vercel.app/ for the page design.
