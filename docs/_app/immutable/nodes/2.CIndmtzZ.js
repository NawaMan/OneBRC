import{s as R,d as E,u as w,g as k,e as x,n as q}from"../chunks/scheduler.Bm1vu1fr.js";import{S as T,i as W,e as h,s as A,c as m,a as b,d as p,f as V,m as d,g as P,h as $,o as v,p as C,k as U,l as y,n as G,q as H,v as B}from"../chunks/index.XvGQ8ST3.js";import{N as j}from"../chunks/NavigationBar.D3c5XmQ4.js";import"../chunks/CtrlBtn.lC4zrQ0f.js";const z=l=>({}),J=l=>({}),F=l=>({}),L=l=>({}),K=l=>({}),O=l=>({});function Q(l){let t,n,s,a,_,o,i;const g=l[1].title,u=E(g,l,l[0],O),S=l[1].subtitle,c=E(S,l,l[0],L),N=l[1].subsubtitle,f=E(N,l,l[0],J);return{c(){t=h("div"),n=h("h1"),u&&u.c(),s=A(),a=h("p"),c&&c.c(),_=A(),o=h("p"),f&&f.c(),this.h()},l(e){t=m(e,"DIV",{class:!0});var r=b(t);n=m(r,"H1",{class:!0});var D=b(n);u&&u.l(D),D.forEach(p),s=V(r),a=m(r,"P",{class:!0});var I=b(a);c&&c.l(I),I.forEach(p),_=V(r),o=m(r,"P",{class:!0});var M=b(o);f&&f.l(M),M.forEach(p),r.forEach(p),this.h()},h(){d(n,"class","title svelte-cs3afi"),d(a,"class","subtitle svelte-cs3afi"),d(o,"class","subsubtitle svelte-cs3afi"),d(t,"class","page svelte-cs3afi")},m(e,r){P(e,t,r),$(t,n),u&&u.m(n,null),$(t,s),$(t,a),c&&c.m(a,null),$(t,_),$(t,o),f&&f.m(o,null),i=!0},p(e,[r]){u&&u.p&&(!i||r&1)&&w(u,g,e,e[0],i?x(g,e[0],r,K):k(e[0]),O),c&&c.p&&(!i||r&1)&&w(c,S,e,e[0],i?x(S,e[0],r,F):k(e[0]),L),f&&f.p&&(!i||r&1)&&w(f,N,e,e[0],i?x(N,e[0],r,z):k(e[0]),J)},i(e){i||(v(u,e),v(c,e),v(f,e),i=!0)},o(e){C(u,e),C(c,e),C(f,e),i=!1},d(e){e&&p(t),u&&u.d(e),c&&c.d(e),f&&f.d(e)}}}function X(l,t,n){let{$$slots:s={},$$scope:a}=t;return l.$$set=_=>{"$$scope"in _&&n(0,a=_.$$scope)},[a,s]}class Y extends T{constructor(t){super(),W(this,t,X,Q,R,{})}}function Z(l){let t,n="One Billion Row Challenge";return{c(){t=h("span"),t.textContent=n,this.h()},l(s){t=m(s,"SPAN",{slot:!0,"data-svelte-h":!0}),B(t)!=="svelte-i6rbpt"&&(t.textContent=n),this.h()},h(){d(t,"slot","title")},m(s,a){P(s,t,a)},p:q,d(s){s&&p(t)}}}function tt(l){let t,n="NawaMan's Java Solution";return{c(){t=h("span"),t.textContent=n,this.h()},l(s){t=m(s,"SPAN",{slot:!0,"data-svelte-h":!0}),B(t)!=="svelte-r78dol"&&(t.textContent=n),this.h()},h(){d(t,"slot","subtitle")},m(s,a){P(s,t,a)},p:q,d(s){s&&p(t)}}}function et(l){let t,n="(2-3 Seconds Without Using Unsafe or Graal VM)";return{c(){t=h("span"),t.textContent=n,this.h()},l(s){t=m(s,"SPAN",{slot:!0,"data-svelte-h":!0}),B(t)!=="svelte-1c10mku"&&(t.textContent=n),this.h()},h(){d(t,"slot","subsubtitle")},m(s,a){P(s,t,a)},p:q,d(s){s&&p(t)}}}function st(l){let t,n,s,a,_;return n=new j({props:{nextLink:"/introduction.html"}}),a=new Y({props:{$$slots:{subsubtitle:[et],subtitle:[tt],title:[Z]},$$scope:{ctx:l}}}),{c(){t=h("div"),U(n.$$.fragment),s=A(),U(a.$$.fragment),this.h()},l(o){t=m(o,"DIV",{class:!0});var i=b(t);y(n.$$.fragment,i),s=V(i),y(a.$$.fragment,i),i.forEach(p),this.h()},h(){d(t,"class","content")},m(o,i){P(o,t,i),G(n,t,null),$(t,s),G(a,t,null),_=!0},p(o,[i]){const g={};i&1&&(g.$$scope={dirty:i,ctx:o}),a.$set(g)},i(o){_||(v(n.$$.fragment,o),v(a.$$.fragment,o),_=!0)},o(o){C(n.$$.fragment,o),C(a.$$.fragment,o),_=!1},d(o){o&&p(t),H(n),H(a)}}}class it extends T{constructor(t){super(),W(this,t,null,st,R,{})}}export{it as component};