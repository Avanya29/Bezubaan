 const fs = require('fs');
const glob = require('glob');

glob.sync('src/**/*.controller.ts').forEach(f => {
  let content = fs.readFileSync(f, 'utf8');
  content = content.replace(/@Controller\('api\//g, "@Controller('");
  fs.writeFileSync(f, content);
  console.log('Fixed ' + f);
});
