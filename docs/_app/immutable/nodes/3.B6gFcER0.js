import{s as z,n as C,b as I,o as q,d as A,c as F,u as J,g as P,a as Q,j as S}from"../chunks/scheduler.DJUZaEzB.js";import{S as D,i as E,e as g,c as w,g as R,r as v,a as _,d as m,n as b,s as x,h as M,o as y,k as T,u as O,p as $,l as j,t as d,b as p,q as k,y as U,z as X}from"../chunks/index.BLoNhNpC.js";/* empty css                        */import{s as L}from"../chunks/scaleMode.q8eS2gCe.js";import{w as Y}from"../chunks/index.ZhpSoJ5_.js";import{C as N}from"../chunks/CtrlBtn.gGN_YDqb.js";function Z(o){let e,t='<span class="text svelte-1ayqez2">© NawaNawa 2024</span> <span class="hover-text svelte-1ayqez2"><a href="https://nawaman.net/contact.html" target="_blank">Copyright <b>NawaNawa &lt;contact@nawaman.net&gt;</b> 2024</a></span>';return{c(){e=g("div"),e.innerHTML=t,this.h()},l(r){e=w(r,"DIV",{class:!0,"data-svelte-h":!0}),R(e)!=="svelte-1nwiu2a"&&(e.innerHTML=t),this.h()},h(){v(e,"class","copyright svelte-1ayqez2")},m(r,s){_(r,e,s)},p:C,i:C,o:C,d(r){r&&m(e)}}}class ee extends D{constructor(e){super(),E(this,e,null,Z,z,{})}}function V(o){let e,t='<ol class="svelte-yuxbuk"><li><a href="./index.html" class="svelte-yuxbuk">Title</a></li> <li><a href="./overview.html" class="svelte-yuxbuk">Overview</a></li> <li><a href="./overview-2.html" class="svelte-yuxbuk">Overview (2)</a></li> <li><a href="./results.html" class="svelte-yuxbuk">Results</a></li> <li><a href="./challenge.html" class="svelte-yuxbuk">One Billion Row Challenge</a></li> <li><a href="./solution.html" class="svelte-yuxbuk">Solution Overview</a></li></ol>';return{c(){e=g("div"),e.innerHTML=t,this.h()},l(r){e=w(r,"DIV",{class:!0,"data-svelte-h":!0}),R(e)!=="svelte-1uldraa"&&(e.innerHTML=t),this.h()},h(){v(e,"class","content svelte-yuxbuk")},m(r,s){_(r,e,s)},d(r){r&&m(e)}}}function te(o){let e,t,r,s;t=new N({props:{text:"ToC",hoverText:"Table of Content",isSelected:o[0]}}),t.$on("click",o[2]);let l=o[0]&&V();return{c(){e=g("div"),b(t.$$.fragment),r=x(),l&&l.c(),this.h()},l(n){e=w(n,"DIV",{class:!0});var a=M(e);y(t.$$.fragment,a),r=T(a),l&&l.l(a),a.forEach(m),this.h()},h(){v(e,"class","toc no-print svelte-yuxbuk"),O(e,"expanded",o[0])},m(n,a){_(n,e,a),$(t,e,null),j(e,r),l&&l.m(e,null),s=!0},p(n,[a]){const f={};a&1&&(f.isSelected=n[0]),t.$set(f),n[0]?l||(l=V(),l.c(),l.m(e,null)):l&&(l.d(1),l=null),(!s||a&1)&&O(e,"expanded",n[0])},i(n){s||(d(t.$$.fragment,n),s=!0)},o(n){p(t.$$.fragment,n),s=!1},d(n){n&&m(e),k(t),l&&l.d()}}}function se(o,e,t){let r,s=Y(!1);I(o,s,f=>t(0,r=f));function l(){s.update(f=>!f)}function n(){s.update(f=>!1)}function a(f){f.ctrlKey&&f.altKey&&f.key==="T"&&(f.preventDefault(),l()),f.key==="Escape"&&(f.preventDefault(),n())}return q(()=>{window.addEventListener("keydown",a)}),A(()=>{window.removeEventListener("keydown",a)}),[r,s,l]}class ne extends D{constructor(e){super(),E(this,e,se,te,z,{})}}function le(o){let e,t,r;return t=new N({props:{text:"M",hoverText:"MODE"}}),t.$on("click",o[0]),{c(){e=g("div"),b(t.$$.fragment),this.h()},l(s){e=w(s,"DIV",{class:!0});var l=M(e);y(t.$$.fragment,l),l.forEach(m),this.h()},h(){v(e,"class","mode no-print svelte-mofkhc")},m(s,l){_(s,e,l),$(t,e,null),r=!0},p:C,i(s){r||(d(t.$$.fragment,s),r=!0)},o(s){p(t.$$.fragment,s),r=!1},d(s){s&&m(e),k(t)}}}function ie(o){function e(){L.update(t=>!t)}return[e]}class re extends D{constructor(e){super(),E(this,e,ie,le,z,{})}}function H(o){let e,t,r,s,l,n,a;const f=o[5].default,c=F(f,o,o[4],null);return t=new ne({}),s=new re({}),n=new ee({}),{c(){c&&c.c(),e=x(),b(t.$$.fragment),r=x(),b(s.$$.fragment),l=x(),b(n.$$.fragment)},l(i){c&&c.l(i),e=T(i),y(t.$$.fragment,i),r=T(i),y(s.$$.fragment,i),l=T(i),y(n.$$.fragment,i)},m(i,u){c&&c.m(i,u),_(i,e,u),$(t,i,u),_(i,r,u),$(s,i,u),_(i,l,u),$(n,i,u),a=!0},p(i,u){c&&c.p&&(!a||u&16)&&J(c,f,i,i[4],a?Q(f,i[4],u,null):P(i[4]),null)},i(i){a||(d(c,i),d(t.$$.fragment,i),d(s.$$.fragment,i),d(n.$$.fragment,i),a=!0)},o(i){p(c,i),p(t.$$.fragment,i),p(s.$$.fragment,i),p(n.$$.fragment,i),a=!1},d(i){i&&(m(e),m(r),m(l)),c&&c.d(i),k(t,i),k(s,i),k(n,i)}}}function ae(o){let e,t,r,s=o[3]&&H(o);return{c(){e=g("div"),t=g("div"),s&&s.c(),this.h()},l(l){e=w(l,"DIV",{class:!0});var n=M(e);t=w(n,"DIV",{class:!0});var a=M(t);s&&s.l(a),a.forEach(m),n.forEach(m),this.h()},h(){v(t,"class","content svelte-fj6pke"),v(e,"class","container svelte-fj6pke"),O(e,"scale-mode",o[2])},m(l,n){_(l,e,n),j(e,t),s&&s.m(t,null),o[6](t),o[7](e),r=!0},p(l,[n]){l[3]?s?(s.p(l,n),n&8&&d(s,1)):(s=H(l),s.c(),d(s,1),s.m(t,null)):s&&(X(),p(s,1,1,()=>{s=null}),U()),(!r||n&4)&&O(e,"scale-mode",l[2])},i(l){r||(d(s),r=!0)},o(l){p(s),r=!1},d(l){l&&m(e),s&&s.d(),o[6](null),o[7](null)}}}function oe(o,e,t){let r;I(o,L,h=>t(8,r=h));let{$$slots:s={},$$scope:l}=e,n,a,f=r,c=!1;const i=1280/720;function u(){if(!n)return;if(!f){t(0,n.style.width="1280px",n),t(0,n.style.height="720px",n),t(1,a.style.transform="scale(1)",a),t(1,a.style.transformOrigin="top left",a);return}const h=window.innerWidth,B=window.innerHeight;h/B>i?(t(0,n.style.height="calc(100vh - 10px)",n),t(0,n.style.width=`${Math.round(n.offsetHeight*i)}px`,n)):(t(0,n.style.width="calc(100vw - 10px)",n),t(0,n.style.height=`${Math.round(n.offsetWidth/i)}px`,n));let G=(n.offsetWidth-10)/1280;t(1,a.style.transform=`scale(${G})`,a),t(1,a.style.transformOrigin="top left",a)}q(()=>{L.subscribe(h=>{t(2,f=h),window.removeEventListener("resize",u),u(),f&&window.addEventListener("resize",u)}),setTimeout(function(){u()},0),t(3,c=!0)});function W(h){S[h?"unshift":"push"](()=>{a=h,t(1,a)})}function K(h){S[h?"unshift":"push"](()=>{n=h,t(0,n)})}return o.$$set=h=>{"$$scope"in h&&t(4,l=h.$$scope)},[n,a,f,c,l,s,W,K]}class _e extends D{constructor(e){super(),E(this,e,oe,ae,z,{})}}export{_e as component};
