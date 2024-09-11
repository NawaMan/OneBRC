import{s as S,f as _,n as x}from"../chunks/scheduler.DJUZaEzB.js";import{S as D,i as N,e as p,s as w,c as h,h as y,k as $,d,r as i,w as M,a as z,l as v,n as T,o as C,p as E,t as G,b as B,q as L,g as J}from"../chunks/index.BLoNhNpC.js";import{N as O}from"../chunks/NavigationBar.BiHT_2fY.js";import{C as P}from"../chunks/ContentPage.D1mHX65b.js";/* empty css                                                      */function U(o){let e,t,s,c,a,n,u;return{c(){e=p("a"),t=p("div"),s=p("img"),a=w(),n=p("img"),this.h()},l(r){e=h(r,"A",{href:!0,target:!0,style:!0});var l=y(e);t=h(l,"DIV",{class:!0});var f=y(t);s=h(f,"IMG",{class:!0,src:!0,alt:!0}),a=$(f),n=h(f,"IMG",{class:!0,src:!0,alt:!0}),f.forEach(d),l.forEach(d),this.h()},h(){i(s,"class","thumbnail svelte-10q0v6u"),_(s.src,c=o[5])||i(s,"src",c),i(s,"alt",o[4]),i(n,"class","qr svelte-10q0v6u"),_(n.src,u=o[3])||i(n,"src",u),i(n,"alt",o[2]),i(t,"class","youtube svelte-10q0v6u"),i(e,"href",o[1]),i(e,"target","_blank"),M(e,"--width",o[0])},m(r,l){z(r,e,l),v(e,t),v(t,s),v(t,a),v(t,n)},p(r,[l]){l&32&&!_(s.src,c=r[5])&&i(s,"src",c),l&16&&i(s,"alt",r[4]),l&8&&!_(n.src,u=r[3])&&i(n,"src",u),l&4&&i(n,"alt",r[2]),l&2&&i(e,"href",r[1]),l&1&&M(e,"--width",r[0])},i:x,o:x,d(r){r&&d(e)}}}function Y(o,e,t){let s,c,a,n,u,{name:r}=e,{width:l="600px"}=e,{youtubeId:f}=e;return o.$$set=m=>{"name"in m&&t(6,r=m.name),"width"in m&&t(0,l=m.width),"youtubeId"in m&&t(7,f=m.youtubeId)},o.$$.update=()=>{o.$$.dirty&64&&t(5,s=`${r}.png`),o.$$.dirty&64&&t(4,c=r),o.$$.dirty&64&&t(3,a=`${r}-QR.png`),o.$$.dirty&64&&t(2,n=`${r}-qr`),o.$$.dirty&128&&t(1,u=`https://www.youtube.com/watch?v=${f}`)},[l,u,n,a,c,s,r,f]}class R extends D{constructor(e){super(),N(this,e,Y,U,S,{name:6,width:0,youtubeId:7})}}function H(o){let e,t,s,c=`<p>The One Billion Row Challenge (1BRC) 
						<a href="https://x.com/gunnarmorling/status/1741839724933751238" data-tooltip=" Annoucement on X " data-placement="top" target="_blank" class="svelte-9ong5z">🔗</a> <a href="https://www.morling.dev/blog/one-billion-row-challenge/" data-tooltip=" 1BRC detail page " data-placement="top" target="_blank" class="svelte-9ong5z">🔗</a>, 
					proposed by Gunnar Morling, 
					is a coding challenge designed to test the limits of <b class="svelte-9ong5z">Java</b>&#39;s performance capabilities. 
					Launched on <b class="svelte-9ong5z">January 1, 2024</b>, 
					the challenge invites participants to create the fastest Java program for processing 
					a text file containing <b class="svelte-9ong5z">one billion rows</b> of temperature measurements.</p> <div class="text-container svelte-9ong5z"><div class="text-left svelte-9ong5z"><span class="bound svelte-9ong5z">CPU BOUND</span></div> <div class="text-right svelte-9ong5z"><img class="drake svelte-9ong5z" src="../drake-yes.png" alt="Drake say &#39;YES&#39;."/>
						August 2024.</div></div>`,a,n,u,r,l,f,m,A,I;return u=new R({props:{name:"../1brc-in-2seconds",youtubeId:"9-S_nZ5gzGE",width:"370px"}}),l=new R({props:{name:"../1brc",youtubeId:"RYjB4sGXNZI",width:"370px"}}),{c(){e=p("table"),t=p("tr"),s=p("td"),s.innerHTML=c,a=w(),n=p("td"),T(u.$$.fragment),r=w(),T(l.$$.fragment),f=w(),m=p("img"),this.h()},l(g){e=h(g,"TABLE",{class:!0});var b=y(e);t=h(b,"TR",{});var q=y(t);s=h(q,"TD",{class:!0,"data-svelte-h":!0}),J(s)!=="svelte-gn7zzg"&&(s.innerHTML=c),a=$(q),n=h(q,"TD",{width:!0});var k=y(n);C(u.$$.fragment,k),r=$(k),C(l.$$.fragment,k),k.forEach(d),q.forEach(d),b.forEach(d),f=$(g),m=h(g,"IMG",{class:!0,src:!0,alt:!0}),this.h()},h(){i(s,"class","text svelte-9ong5z"),i(n,"width","370px"),i(e,"class","main-table svelte-9ong5z"),i(m,"class","glasses svelte-9ong5z"),_(m.src,A="../ThugLifeGlasses.png")||i(m,"src",A),i(m,"alt","Glasses")},m(g,b){z(g,e,b),v(e,t),v(t,s),v(t,a),v(t,n),E(u,n,null),v(n,r),E(l,n,null),z(g,f,b),z(g,m,b),I=!0},p:x,i(g){I||(G(u.$$.fragment,g),G(l.$$.fragment,g),I=!0)},o(g){B(u.$$.fragment,g),B(l.$$.fragment,g),I=!1},d(g){g&&(d(e),d(f),d(m)),L(u),L(l)}}}function X(o){let e,t,s,c;return e=new P({props:{title:"Overview",$$slots:{default:[H]},$$scope:{ctx:o}}}),s=new O({props:{prevLink:"./overview.html",nextLink:"./results.html"}}),{c(){T(e.$$.fragment),t=w(),T(s.$$.fragment)},l(a){C(e.$$.fragment,a),t=$(a),C(s.$$.fragment,a)},m(a,n){E(e,a,n),z(a,t,n),E(s,a,n),c=!0},p(a,[n]){const u={};n&1&&(u.$$scope={dirty:n,ctx:a}),e.$set(u)},i(a){c||(G(e.$$.fragment,a),G(s.$$.fragment,a),c=!0)},o(a){B(e.$$.fragment,a),B(s.$$.fragment,a),c=!1},d(a){a&&d(t),L(e,a),L(s,a)}}}class K extends D{constructor(e){super(),N(this,e,null,X,S,{})}}export{K as component};
