// @ts-ignore
import fs from 'fs';
// @ts-ignore
import path from 'path';

export async function load() {
  const filePath = path.resolve('static', '', 'CalculateAverage_nawaman.java');
  const javaCode = fs.readFileSync(filePath, 'utf-8');
  return { javaCode };
}
