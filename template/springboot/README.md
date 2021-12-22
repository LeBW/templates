# Template: Springboot
The Springboot template using Maven.

## Usage
1. Create a function
```bash
faas-cli template pull https://github.com/LeBW/templates.git
faas-cli new --lang springboot <func-name>
```
2. Write your handler.

3. Build the image, push the image and deploy the function.
```bash
faas-cli build -f <func-name>.yml
faas-cli push -f <func-name>.yml
faas-cli deploy -f <func-name>.yml
```