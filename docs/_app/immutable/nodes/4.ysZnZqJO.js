import{s as _,n as y}from"../chunks/scheduler.DvCM5tHg.js";import{S as k,i as C,k as b,s as x,l as c,f as w,m as d,g as v,n as m,o as u,d as i,p,e as f,c as g,q as h,r as $}from"../chunks/index.DvlnuOep.js";import{N as B}from"../chunks/NavigationBar.0fE2o1XS.js";import{C as M}from"../chunks/ContentPage.BWcYxq9R.js";function S(r){let e,o='<ul class="svelte-1ob35tv"><li>Read a <b class="svelte-1ob35tv">text file</b> containing <b class="svelte-1ob35tv">1 billion rows</b> of measurements.</li> <li>Each line contains a weather <b class="svelte-1ob35tv">station name</b> and <b class="svelte-1ob35tv">temperature</b>:\n				<ul class="svelte-1ob35tv"><li>Rows are delimited by a newline character (<b class="svelte-1ob35tv">&#39;\\n&#39;</b>).</li> <li>Station name and temperature are separated by a semicolon.</li> <li>Station name is encoded in <b class="svelte-1ob35tv">UTF-8</b> and is <b class="svelte-1ob35tv">100 bytes</b> or shorter.</li> <li>The temperature ranges from -99.9 to 99.9 (<b class="svelte-1ob35tv">1-2 digits before</b> the decimal point and <b class="svelte-1ob35tv">1 digit after</b>).</li></ul></li> <li>Calculate the <b class="svelte-1ob35tv">minimum</b>, <b class="svelte-1ob35tv">maximum</b>, and <b class="svelte-1ob35tv">average</b> temperatures for <b class="svelte-1ob35tv">each station</b>.</li> <li>Print the results in the format of Java <b class="svelte-1ob35tv">sorted-map `toString()`</b>.</li> <li><b class="svelte-1ob35tv">No external dependencies</b>.</li> <li>All source code should be in a <b class="svelte-1ob35tv">single file</b>.</li> <li>Benchmark on a <b class="svelte-1ob35tv">32-core CPU</b> with <b class="svelte-1ob35tv">128 GB RAM</b>.</li></ul>',a,l,t=`<table class="svelte-1ob35tv"><tr class="svelte-1ob35tv"><td colspan="2" style="text-align: left;" class="svelte-1ob35tv"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="3" y1="4" x2="16" y2="4"></line><line x1="3" y1="8" x2="11" y2="8"></line><line x1="3" y1="12" x2="16" y2="12"></line><line x1="3" y1="16" x2="11" y2="16"></line></svg>
			   	measurements.txt</td></tr> <tr class="svelte-1ob35tv"><td class="svelte-1ob35tv">1</td><td class="svelte-1ob35tv">Ottawa;14.2</td></tr> <tr class="svelte-1ob35tv"><td class="svelte-1ob35tv">2</td><td class="svelte-1ob35tv">Bangkok;44.0</td></tr> <tr class="svelte-1ob35tv"><td class="svelte-1ob35tv">3</td><td class="svelte-1ob35tv">Sydney;-2.5</td></tr> <tr class="svelte-1ob35tv"><td class="svelte-1ob35tv">4</td><td class="svelte-1ob35tv">Łódź;-20.4</td></tr> <tr class="blur svelte-1ob35tv"><td class="svelte-1ob35tv">455</td><td class="svelte-1ob35tv">New York;20.7</td></tr> <tr class="blur svelte-1ob35tv"><td class="svelte-1ob35tv">15,466</td><td class="svelte-1ob35tv">Mars;-24.7</td></tr> <tr class="blur svelte-1ob35tv"><td class="svelte-1ob35tv">1,546,536</td><td class="svelte-1ob35tv">Venus;84.5</td></tr> <tr class="svelte-1ob35tv"><td class="svelte-1ob35tv">1,000,000,000</td><td class="svelte-1ob35tv">Budapest;14.9</td></tr></table>`;return{c(){e=f("div"),e.innerHTML=o,a=x(),l=f("div"),l.innerHTML=t,this.h()},l(s){e=g(s,"DIV",{class:!0,"data-svelte-h":!0}),h(e)!=="svelte-1x3jcnu"&&(e.innerHTML=o),a=w(s),l=g(s,"DIV",{class:!0,"data-svelte-h":!0}),h(l)!=="svelte-tyw2r1"&&(l.innerHTML=t),this.h()},h(){$(e,"class","left svelte-1ob35tv"),$(l,"class","right svelte-1ob35tv")},m(s,n){v(s,e,n),v(s,a,n),v(s,l,n)},p:y,d(s){s&&(i(e),i(a),i(l))}}}function T(r){let e,o,a,l;return e=new M({props:{title:"One Billion Row Challenge",$$slots:{default:[S]},$$scope:{ctx:r}}}),a=new B({props:{prevLink:"/overview.html"}}),{c(){b(e.$$.fragment),o=x(),b(a.$$.fragment)},l(t){c(e.$$.fragment,t),o=w(t),c(a.$$.fragment,t)},m(t,s){d(e,t,s),v(t,o,s),d(a,t,s),l=!0},p(t,[s]){const n={};s&1&&(n.$$scope={dirty:s,ctx:t}),e.$set(n)},i(t){l||(m(e.$$.fragment,t),m(a.$$.fragment,t),l=!0)},o(t){u(e.$$.fragment,t),u(a.$$.fragment,t),l=!1},d(t){t&&i(o),p(e,t),p(a,t)}}}class R extends k{constructor(e){super(),C(this,e,null,T,_,{})}}export{R as component};
