import{s as w,n as y}from"../chunks/scheduler.BhpBgdbA.js";import{S as k,i as C,k as o,s as x,l as c,f as _,m as d,g as n,n as h,o as m,d as b,p as u,e as p,c as f,q as g,r as $}from"../chunks/index.De3LKqI6.js";import{N as B}from"../chunks/NavigationBar.B8pqxQY2.js";import{C as M}from"../chunks/ContentPage.BwcsrG37.js";function L(v){let s,i='<ul class="svelte-1rab3h1"><li>Read a <b class="svelte-1rab3h1">text file</b> containing <b class="svelte-1rab3h1">1 billion rows</b> of measurements.</li> <li>Each line contains a weather <b class="svelte-1rab3h1">station name</b> and <b class="svelte-1rab3h1">temperature</b>:\n				<ul class="svelte-1rab3h1"><li>Rows are delimited by a newline character (<b class="svelte-1rab3h1">&#39;\\n&#39;</b>).</li> <li>Station name and temperature are separated by a semicolon.</li> <li>Station name is encoded in <b class="svelte-1rab3h1">UTF-8</b> and is <b class="svelte-1rab3h1">100 bytes</b> or shorter.</li> <li>The temperature ranges from -99.9 to 99.9 (<b class="svelte-1rab3h1">1-2 digits before</b> the decimal point and <b class="svelte-1rab3h1">1 digit after</b>).</li></ul></li> <li>Calculate the <b class="svelte-1rab3h1">minimum</b>, <b class="svelte-1rab3h1">maximum</b>, and <b class="svelte-1rab3h1">average</b> temperatures for <b class="svelte-1rab3h1">each station</b>.</li> <li>Print the results in the format of Java <b class="svelte-1rab3h1">sorted-map `toString()`</b>.</li> <li><b class="svelte-1rab3h1">No external dependencies</b>.</li> <li>All source code should be in a <b class="svelte-1rab3h1">single file</b>.</li> <li>Benchmark on a <b class="svelte-1rab3h1">32-core CPU</b> with <b class="svelte-1rab3h1">128 GB RAM</b>.</li></ul>',l,a,e=`<div class="tab svelte-1rab3h1"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="3" y1="4" x2="16" y2="4"></line><line x1="3" y1="8" x2="11" y2="8"></line><line x1="3" y1="12" x2="16" y2="12"></line><line x1="3" y1="16" x2="11" y2="16"></line></svg>
			measurements.txt</div> <table class="editor svelte-1rab3h1"><tr><td class="linenums svelte-1rab3h1"><div class="svelte-1rab3h1">1</div> <div class="svelte-1rab3h1">2</div> <div class="svelte-1rab3h1">3</div> <div class="svelte-1rab3h1">4</div> <div class="blur svelte-1rab3h1">455</div> <div class="blur svelte-1rab3h1">15,466</div> <div class="blur svelte-1rab3h1">1,546,536</div> <div class="svelte-1rab3h1">1,000,000,000</div></td> <td class="code svelte-1rab3h1"><div class="svelte-1rab3h1">Ottawa;14.2</div> <div class="svelte-1rab3h1">Bangkok;44.0</div> <div class="svelte-1rab3h1">Sydney;-2.5</div> <div class="svelte-1rab3h1">Łódź;-20.4</div> <div class="blur svelte-1rab3h1">Mercury;-50.7</div> <div class="blur svelte-1rab3h1">Mars;-4.7</div> <div class="blur svelte-1rab3h1">Venus;84.5</div> <div class="svelte-1rab3h1">Budapest;14.9</div></td></tr></table> <p>File size: <b class="svelte-1rab3h1">13GB</b></p>`;return{c(){s=p("div"),s.innerHTML=i,l=x(),a=p("div"),a.innerHTML=e,this.h()},l(t){s=f(t,"DIV",{class:!0,"data-svelte-h":!0}),g(s)!=="svelte-1x3jcnu"&&(s.innerHTML=i),l=_(t),a=f(t,"DIV",{class:!0,"data-svelte-h":!0}),g(a)!=="svelte-cdd63y"&&(a.innerHTML=e),this.h()},h(){$(s,"class","left svelte-1rab3h1"),$(a,"class","right svelte-1rab3h1")},m(t,r){n(t,s,r),n(t,l,r),n(t,a,r)},p:y,d(t){t&&(b(s),b(l),b(a))}}}function S(v){let s,i,l,a;return s=new M({props:{title:"One Billion Row Challenge",$$slots:{default:[L]},$$scope:{ctx:v}}}),l=new B({props:{prevLink:"/results.html",nextLink:"/solution-overview.html"}}),{c(){o(s.$$.fragment),i=x(),o(l.$$.fragment)},l(e){c(s.$$.fragment,e),i=_(e),c(l.$$.fragment,e)},m(e,t){d(s,e,t),n(e,i,t),d(l,e,t),a=!0},p(e,[t]){const r={};t&1&&(r.$$scope={dirty:t,ctx:e}),s.$set(r)},i(e){a||(h(s.$$.fragment,e),h(l.$$.fragment,e),a=!0)},o(e){m(s.$$.fragment,e),m(l.$$.fragment,e),a=!1},d(e){e&&b(i),u(s,e),u(l,e)}}}class N extends k{constructor(s){super(),C(this,s,null,S,w,{})}}export{N as component};
