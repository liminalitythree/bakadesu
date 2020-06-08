const decoder = new TextDecoder('utf-8');

const text = decoder.decode(await Deno.readFile('helloworld.template'));

const res = text.replace(/\(/g, '🤜')
    .replace(/\)/g, '🤛')
    .replace(/\+/g, '🥺')
    .replace(/\[/g, '👉')
    .replace(/\]/g, '👈')
    .replace(/\,/g, '〰️')
    .replace(/\ /g, '')
    .replace(/[0-9]+/g, clockReplacer);

function clockReplacer(match: string) {
    const int = parseInt(match)
    const b12 = int.toString(12)
    return b12.replace(/0/g, '🕐')
        .replace(/1/g, '🕑')
        .replace(/2/g, '🕒')
        .replace(/3/g, '🕓')
        .replace(/4/g, '🕔')
        .replace(/5/g, '🕕')
        .replace(/6/g, '🕖')
        .replace(/7/g, '🕗')
        .replace(/8/g, '🕘')
        .replace(/9/g, '🕙')
        .replace(/a/g, '🕚')
        .replace(/b/g, '🕛');
}

console.log(res);
